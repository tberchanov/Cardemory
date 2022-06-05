import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class SaveCardSet extends UseCase<CardSet, CardSet> {
  final CardSetRepository _cardSetRepository;

  SaveCardSet(this._cardSetRepository);

  @override
  Future<Either<Failure, CardSet>> call(CardSet param) async {
    return await _cardSetRepository.saveCardSet(param);
  }
}