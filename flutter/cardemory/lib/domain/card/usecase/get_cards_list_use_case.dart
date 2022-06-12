import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardsListUseCase extends UseCase<List<Card>, int> {
  final CardRepository _cardRepository;

  GetCardsListUseCase(this._cardRepository);

  @override
  // ignore: avoid_renaming_method_parameters
  Future<Either<Failure, List<Card>>> call(int cardSetId) async {
    return await _cardRepository.getCards(cardSetId);
  }
}
