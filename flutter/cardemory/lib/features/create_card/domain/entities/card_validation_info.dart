import 'package:equatable/equatable.dart';

class CardValidationInfo extends Equatable {
  final bool isSuccess;
  final String? titleValidationMessage;
  final String? descriptionValidationMessage;

  const CardValidationInfo(this.isSuccess, this.titleValidationMessage, this.descriptionValidationMessage);

  const CardValidationInfo.success()
      : isSuccess = true,
        titleValidationMessage = null,
        descriptionValidationMessage = null;

  const CardValidationInfo.fail(
    this.titleValidationMessage,
    this.descriptionValidationMessage,
  ) : isSuccess = false;

  @override
  List<Object?> get props => [isSuccess, titleValidationMessage, descriptionValidationMessage];
}
