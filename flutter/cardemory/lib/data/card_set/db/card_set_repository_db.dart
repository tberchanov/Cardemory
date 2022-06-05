import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card_set/db/card_set_db_model.dart';
import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:dartz/dartz.dart';
import 'package:sqflite/sqflite.dart';

class CardSetRepositoryDb extends CardSetRepository {
  final Future<Database> _dbFuture;

  CardSetRepositoryDb(this._dbFuture);

  CardSetRepositoryDb.fromDB(DB db) : _dbFuture = db.create();

  @override
  Future<Either<Failure, List<CardSet>>> getCardSets() async {
    final db = await _dbFuture;
    final query = await db.query(CardSetTable.name);
    return Right(
      query.map((map) => CardSetDbModel.fromMap(map).toEntity()).toList(),
    );
  }

  @override
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet) async {
    final db = await _dbFuture;
    final cardSetId = await db.insert(
      CardSetTable.name,
      CardSetDbModel.fromEntity(cardSet).toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return Right(cardSet.copyWith(id: cardSetId));
  }

  @override
  Future<Either<Failure, CardSet?>> getCardSet(int id) async {
    final db = await _dbFuture;
    final query = await db.query(
      CardSetTable.name,
      where: "${CardSetTable.propertyId} = ?",
      whereArgs: [id],
    );
    return Right(
      query.map((map) => CardSetDbModel.fromMap(map).toEntity()).first,
    );
  }
}
