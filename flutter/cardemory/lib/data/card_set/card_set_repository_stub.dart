import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/extension/list_ext.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:cardemory/di/injection_container.dart';
import 'package:dartz/dartz.dart';
import 'package:injectable/injectable.dart';

@envWeb
@LazySingleton(as: CardSetRepository)
class CardSetRepositoryStub extends CardSetRepository {
  final List<CardSet> _cardSets = [
    const CardSet(id: 0, name: "Card Set 1"),
    const CardSet(id: 1, name: "Card Set 2"),
    const CardSet(id: 2, name: "Card Set 3"),
  ];

  @override
  Future<Either<Failure, List<CardSet>>> getCardSets() async {
    return Right(_cardSets);
  }

  @override
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet) async {
    final cardSetWithId = cardSet.copyWith(id: _cardSets.length);
    _cardSets.add(cardSetWithId);
    return Right(cardSetWithId);
  }

  @override
  Future<Either<Failure, CardSet?>> getCardSet(int id) async {
    return Right(_cardSets.firstOrNull((element) => element.id == id));
  }
}
