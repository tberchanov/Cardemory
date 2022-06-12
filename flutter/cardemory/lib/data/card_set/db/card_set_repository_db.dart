import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card_set/db/card_set_db_model.dart';
import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:cardemory/di/injection_container.dart';
import 'package:dartz/dartz.dart';
import 'package:injectable/injectable.dart';
import 'package:sqflite/sqflite.dart';

@envMobile
@LazySingleton(as: CardSetRepository)
class CardSetRepositoryDb extends CardSetRepository {
  final Database _db;

  CardSetRepositoryDb(this._db);

  @override
  Future<Either<Failure, List<CardSet>>> getCardSets() async {
    final query = await _db.query(CardSetTable.name);
    return Right(
      query.map((map) => CardSetDbModel.fromMap(map).toEntity()).toList(),
    );
  }

  @override
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet) async {
    final cardSetId = await _db.insert(
      CardSetTable.name,
      CardSetDbModel.fromEntity(cardSet).toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return Right(cardSet.copyWith(id: cardSetId));
  }

  @override
  Future<Either<Failure, CardSet?>> getCardSet(int id) async {
    final query = await _db.query(
      CardSetTable.name,
      where: "${CardSetTable.propertyId} = ?",
      whereArgs: [id],
    );
    return Right(
      query.map((map) => CardSetDbModel.fromMap(map).toEntity()).first,
    );
  }
}
