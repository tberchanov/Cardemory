import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:dartz/dartz.dart';

class GetCardsList extends UseCase<List<Card>, NoParams> {
  final CardRepository _cardRepository;

  GetCardsList(this._cardRepository);

  @override
  Future<Either<Failure, List<Card>>> call(NoParams param) async {
    return await _cardRepository.getCards();
  }
}
