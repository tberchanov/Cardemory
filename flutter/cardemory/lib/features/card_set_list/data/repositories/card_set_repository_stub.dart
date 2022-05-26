import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class CardSetRepositoryStub extends CardSetRepository {

  @override
  Future<Either<Failure, List<CardSet>>> getCardSets() async {
    return const Right([]);
  }

  @override
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet) async {
    return Right(cardSet);
  }
}