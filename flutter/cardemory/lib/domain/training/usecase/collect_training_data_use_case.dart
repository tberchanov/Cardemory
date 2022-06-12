import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/training/entity/training_data.dart';
import 'package:dartz/dartz.dart';

class CollectTrainingDataUseCase extends UseCase<TrainingData, CardSet> {
  @override
  Future<Either<Failure, TrainingData>> call(CardSet param) async {
    // TODO: implement call
    await Future.delayed(const Duration(seconds: 2));
    return Right(TrainingData(
      param,
      [
        Card(param.id, "Title 1", "Description 1", id: 1),
        Card(param.id, "Title 2", "Description 2", id: 2),
        Card(param.id, "Title 3", "Description 3", id: 3),
        Card(param.id, "Title 4", "Description 4", id: 4),
      ],
    ));
  }
}
