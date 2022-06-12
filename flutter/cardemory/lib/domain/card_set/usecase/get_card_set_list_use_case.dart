import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardSetListUseCase extends UseCase<List<CardSet>, NoParams> {
  final CardSetRepository _cardSetRepository;

  GetCardSetListUseCase(this._cardSetRepository);

  @override
  Future<Either<Failure, List<CardSet>>> call(param) async {
    return await _cardSetRepository.getCardSets();
  }
}