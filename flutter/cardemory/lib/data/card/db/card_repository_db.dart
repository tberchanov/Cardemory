import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card/db/card_table.dart';
import 'package:cardemory/data/card/db/mappers/card_to_map_mapper.dart';
import 'package:cardemory/data/card/db/mappers/map_to_card_mapper.dart';
import 'package:cardemory/di/injection_container.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:dartz/dartz.dart';
import 'package:injectable/injectable.dart';
import 'package:sqflite/sqlite_api.dart';

@envMobile
@LazySingleton(as: CardRepository)
class CardRepositoryDb extends CardRepository {
  final Database _db;
  final CardToMapMapper _cardToMapMapper;
  final MapToCardMapper _mapToCardMapper;

  CardRepositoryDb(this._db, this._cardToMapMapper, this._mapToCardMapper);

  @override
  Future<Either<Failure, List<Card>>> getCards(int cardSetId) async {
    final query = await _db.query(
      CardTable.name,
      where: "${CardTable.propertyCardSetId} = ?",
      whereArgs: [cardSetId],
    );
    return Right(query.map(_mapToCardMapper.map).toList());
  }

  @override
  Future<Either<Failure, Card>> saveCard(Card card) async {
    final cardId = await _db.insert(
      CardTable.name,
      _cardToMapMapper.map(card),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return Right(card.copyWith(id: cardId));
  }
}
