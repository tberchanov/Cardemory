import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/entity/card_set_validation_info.dart';
import 'package:dartz/dartz.dart';

class ValidateCardSetUseCase extends UseCase<CardSetValidationInfo, CardSet> {
  @override
  Future<Either<Failure, CardSetValidationInfo>> call(CardSet param) async {
    return param.name.trim().isEmpty
        ? const Right(CardSetValidationInfo.fail("Let's enter the name"))
        : const Right(CardSetValidationInfo.success());
  }
}
