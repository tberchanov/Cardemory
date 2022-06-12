import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:cardemory/di/injection_container.dart';
import 'package:dartz/dartz.dart';
import 'package:injectable/injectable.dart';

@envWeb
@LazySingleton(as: CardRepository)
class CardRepositoryStub extends CardRepository {
  final List<Card> _cards = [];

  @override
  Future<Either<Failure, List<Card>>> getCards(int cardSetId) async {
    return Right(_cards.where((card) => card.cardSetId == cardSetId).toList());
  }

  @override
  Future<Either<Failure, Card>> saveCard(Card card) async {
    final cardWithId = card.copyWith(id: _cards.length);
    _cards.add(cardWithId);
    return Right(cardWithId);
  }
}
