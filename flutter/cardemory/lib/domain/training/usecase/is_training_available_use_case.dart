import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/training/entity/training_availability_data.dart';
import 'package:cardemory/domain/training/usecase/get_min_cards_for_training_use_case.dart';

class IsTrainingAvailableUseCase {

  final GetMinCardsForTrainingUseCase _getMinCardsForTrainingUseCase;
  late final _minCardsForTraining = _getMinCardsForTrainingUseCase();

  IsTrainingAvailableUseCase(this._getMinCardsForTrainingUseCase);

  TrainingAvailabilityData call(List<Card> cardsForTraining) {
    final isTrainingAvailable = cardsForTraining.length >= _minCardsForTraining;
    String? message;

    if (!isTrainingAvailable) {
      final cardsDistinction = _minCardsForTraining - cardsForTraining.length;
      message = cardsDistinction == 1
          ? "Not enough cards for training. $cardsDistinction card more."
          : "Not enough cards for training. $cardsDistinction cards more.";
    }

    return TrainingAvailabilityData(isTrainingAvailable, message);
  }
}
