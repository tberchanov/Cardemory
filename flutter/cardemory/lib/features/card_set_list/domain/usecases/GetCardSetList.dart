import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardSetList {
  final CardSetRepository _cardSetRepository;

  GetCardSetList(this._cardSetRepository);

  Future<Either<Failure, List<CardSet>>> execute() async {
    return await _cardSetRepository.getCardSets();
  }
}