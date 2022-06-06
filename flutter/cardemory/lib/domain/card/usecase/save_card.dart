import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:dartz/dartz.dart';

class SaveCard extends UseCase<Card, Card> {
  final CardRepository _cardRepository;

  SaveCard(this._cardRepository);

  @override
  Future<Either<Failure, Card>> call(Card param) async {
    return await _cardRepository.saveCard(param);
  }
}
