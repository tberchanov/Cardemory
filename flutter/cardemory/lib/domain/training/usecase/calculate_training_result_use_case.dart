import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/training/entity/training_result.dart';
import 'package:cardemory/domain/training/usecase/get_cards_for_training_amount_use_case.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter/foundation.dart';

class CalculateTrainingResultUseCase extends UseCase<TrainingResult, int> {
  final GetCardsForTrainingAmountUseCase _getCardsForTrainingAmountUseCase;

  CalculateTrainingResultUseCase(this._getCardsForTrainingAmountUseCase);

  @override
  // ignore: avoid_renaming_method_parameters
  Future<Either<Failure, TrainingResult>> call(int rememberedCards) {
    final cardsForTrainingAmount = _getCardsForTrainingAmountUseCase();
    if (rememberedCards > cardsForTrainingAmount) {
      return SynchronousFuture(Left(
        FailureWithMessage("rememberedCards is more then cards for training: $rememberedCards"),
      ));
    }

    if (rememberedCards == 0) {
      return _buildResult(TrainingResult.min);
    } else if (rememberedCards <= 2) {
      return _buildResult(TrainingResult.veryBad);
    } else if (rememberedCards <= 4) {
      return _buildResult(TrainingResult.bad);
    } else if (rememberedCards == 5) {
      return _buildResult(TrainingResult.normal);
    } else if (rememberedCards <= 7) {
      return _buildResult(TrainingResult.good);
    } else if (rememberedCards <= 9) {
      return _buildResult(TrainingResult.veryGood);
    } else if (rememberedCards == cardsForTrainingAmount) {
      return _buildResult(TrainingResult.max);
    }

    return SynchronousFuture(Left(
      FailureWithMessage("rememberedCards is unexpected: $rememberedCards"),
    ));
  }

  Future<Either<Failure, TrainingResult>> _buildResult(TrainingResult trainingResult) =>
      SynchronousFuture(Right(trainingResult));
}
