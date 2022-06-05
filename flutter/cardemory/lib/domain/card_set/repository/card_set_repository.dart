import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:dartz/dartz.dart';

abstract class CardSetRepository {
  Future<Either<Failure, List<CardSet>>> getCardSets();
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet);
  Future<Either<Failure, CardSet?>> getCardSet(int id);
}