import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardSet extends UseCase<CardSet?, int> {
  final CardSetRepository _cardSetRepository;

  GetCardSet(this._cardSetRepository);

  @override
  Future<Either<Failure, CardSet?>> call(int param) async {
    return await _cardSetRepository.getCardSet(param);
  }
}