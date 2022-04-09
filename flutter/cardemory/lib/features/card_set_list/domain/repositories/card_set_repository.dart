import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:dartz/dartz.dart';

abstract class CardSetRepository {
  Future<Either<Failure, List<CardSet>>> getCardSets();
  Future<Either<Failure, CardSet>> saveCardSet(CardSet cardSet);
}