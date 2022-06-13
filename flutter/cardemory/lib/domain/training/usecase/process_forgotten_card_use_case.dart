import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:cardemory/domain/memory/memory_manager.dart';
import 'package:cardemory/domain/training/entity/card_memory_holder.dart';
import 'package:dartz/dartz.dart';

class ProcessForgottenCardUseCase extends UseCase<Card, Card> {
  final CardRepository _cardRepository;
  final MemoryManager _memoryManager;

  ProcessForgottenCardUseCase(this._cardRepository, this._memoryManager);

  @override
  Future<Either<Failure, Card>> call(Card param) async {
    final cardMemoryHolder = CardMemoryHolder(param);
    _memoryManager.forget(cardMemoryHolder);
    final result = await _cardRepository.saveCard(cardMemoryHolder.card);
    return result.isLeft() ? Left(result.leftOrError()) : Right(cardMemoryHolder.card);
  }
}
