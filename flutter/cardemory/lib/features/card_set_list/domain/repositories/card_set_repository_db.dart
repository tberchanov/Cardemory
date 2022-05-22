import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/data/card_set_db_model.dart';
import 'package:cardemory/features/card_set_list/data/card_set_table.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:dartz/dartz.dart';
import 'package:sqflite/sqflite.dart';

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
}
