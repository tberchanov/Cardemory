import 'package:equatable/equatable.dart';

class CardSetValidationInfo extends Equatable {
  final bool isSuccess;
  final String? nameValidationMessage;

  const CardSetValidationInfo(this.isSuccess, this.nameValidationMessage);

  const CardSetValidationInfo.success()
      : isSuccess = true,
        nameValidationMessage = null;

  const CardSetValidationInfo.fail(
    this.nameValidationMessage,
  ) : isSuccess = false;

  @override
  List<Object?> get props => [isSuccess, nameValidationMessage ?? ""];
}
