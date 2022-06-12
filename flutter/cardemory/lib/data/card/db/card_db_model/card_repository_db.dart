import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card/db/card_db_model/card_db_model.dart';
import 'package:cardemory/data/card/db/card_db_model/card_table.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:cardemory/di/injection_container.dart';
import 'package:dartz/dartz.dart';
import 'package:injectable/injectable.dart';
import 'package:sqflite/sqlite_api.dart';

@envMobile
@LazySingleton(as: CardRepository)
class CardRepositoryDb extends CardRepository {
  final Database _db;

  CardRepositoryDb(this._db);

  @override
  Future<Either<Failure, List<Card>>> getCards(int cardSetId) async {
    final query = await _db.query(
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
    final cardId = await _db.insert(
      CardTable.name,
      CardDbModel.fromEntity(card).toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return Right(card.copyWith(id: cardId));
  }
}
