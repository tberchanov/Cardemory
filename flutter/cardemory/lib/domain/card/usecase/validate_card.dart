import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/entity/card_validation_info.dart';
import 'package:dartz/dartz.dart';

class ValidateCard extends UseCase<CardValidationInfo, Card> {
  @override
  Future<Either<Failure, CardValidationInfo>> call(Card param) async {
    final title = param.title.trim();
    final description = param.description.trim();

    if (title.isEmpty || description.isEmpty) {
      return Right(CardValidationInfo.fail(
        title.isEmpty ? "Title should not be empty" : null,
        description.isEmpty ? "Come on, enter the description" : null,
      ));
    } else {
      return const Right(CardValidationInfo.success());
    }
  }
}
