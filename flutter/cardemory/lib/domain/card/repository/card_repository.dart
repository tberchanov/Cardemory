import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:dartz/dartz.dart';

abstract class CardRepository {
  Future<Either<Failure, List<Card>>> getCards();

  Future<Either<Failure, Card>> saveCard(Card card);
}
