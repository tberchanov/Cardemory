import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card/db/card_db_model/card_db_model.dart';
import 'package:cardemory/data/card/db/card_db_model/card_table.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:dartz/dartz.dart';
import 'package:sqflite/sqlite_api.dart';

class CardRepositoryDb extends CardRepository {
  final Future<Database> _dbFuture;

  CardRepositoryDb(this._dbFuture);

  CardRepositoryDb.fromDB(DB db) : _dbFuture = db.create();

  @override
  Future<Either<Failure, List<Card>>> getCards(int cardSetId) async {
    final db = await _dbFuture;
    final query = await db.query(
      CardTable.name,
      where: "${CardTable.propertyCardSetId} = ?",
      whereArgs: [cardSetId],
    );
    return Right(
      query.map((map) => CardDbModel.fromMap(map).toEntity()).toList(),
    );
  }

  @override
  Future<Either<Failure, Card>> saveCard(Card card) async {
    final db = await _dbFuture;
    final cardId = await db.insert(
      CardTable.name,
      CardDbModel.fromEntity(card).toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return Right(card.copyWith(id: cardId));
  }
}
