import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardSet extends UseCase<CardSet?, int> {
  final CardSetRepository _cardSetRepository;

  GetCardSet(this._cardSetRepository);

  @override
  Future<Either<Failure, CardSet?>> call(int param) async {
    return await _cardSetRepository.getCardSet(param);
  }
}