import 'dart:math';

import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/core/extension/list_ext.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/repository/card_repository.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/memory/memory_manager.dart';
import 'package:cardemory/domain/training/entity/training_data.dart';
import 'package:cardemory/domain/training/usecase/get_cards_for_training_amount_use_case.dart';
import 'package:dartz/dartz.dart';
import 'package:logging/logging.dart';

class CollectTrainingDataUseCase extends UseCase<TrainingData, CardSet> {
  static final _log = Logger("CollectTrainingDataUseCase");
  final CardRepository _cardRepository;
  final GetCardsForTrainingAmountUseCase _getMinCardsForTrainingUseCase;
  late final _minCardsForTraining = _getMinCardsForTrainingUseCase();
  final _random = Random();

  CollectTrainingDataUseCase(this._cardRepository, this._getMinCardsForTrainingUseCase);

  @override
  Future<Either<Failure, TrainingData>> call(CardSet param) async {
    final cardsResult = await _cardRepository.getCards(param.id);
    _log.info("cards loaded: $cardsResult");
    if (cardsResult.isLeft()) {
      return Left(cardsResult.leftOrError());
    }

    final List<Card> cards = cardsResult.rightOrError();
    final List<Card> sortedInvertedCards = cards.map(_invertCardMemoryRank).toList();
    sortedInvertedCards.sort((a, b) => b.memoryRank.compareTo(a.memoryRank));
    _log.info("sortedInvertedCards prepared: $sortedInvertedCards");
    List<Card> selectedCards = [];

    while (selectedCards.length < _minCardsForTraining) {
      final double memoryRankSum = sortedInvertedCards.map((e) => e.memoryRank).reduce((a, b) => a + b);
      final randomSelector = memoryRankSum * _random.nextDouble();

      _log.info("Looking for card. memoryRankSum: $memoryRankSum randomSelector: $randomSelector");

      double memoryRankThreshold = 0.0;
      for (final card in sortedInvertedCards) {
        memoryRankThreshold += card.memoryRank;
        if (randomSelector <= memoryRankThreshold) {
          final selectedCard = cards.firstOrNull((element) => element.id == card.id);
          if (selectedCard != null) {
            selectedCards.add(selectedCard);
            _log.info("card selected: $selectedCard");
            sortedInvertedCards.remove(card);
          }
          break;
        }
      }
    }

    return Right(TrainingData(param, selectedCards));
  }

  Card _invertCardMemoryRank(Card card) {
    return card.copyWith(memoryRank: MemoryManager.maxMemoryRank - card.memoryRank);
  }
}
