import 'package:equatable/equatable.dart';

abstract class CreateCardState extends Equatable {
  static final initial = CreateCardInitialState();

  static InvalidFieldState invalidField(
    String? titleMessage,
    String? descriptionMessage,
  ) =>
      InvalidFieldState(titleMessage, descriptionMessage);

  @override
  List<Object> get props => [];
}

class CreateCardInitialState extends CreateCardState {}

class InvalidFieldState extends CreateCardState {
  final String? titleMessage;
  final String? descriptionMessage;

  InvalidFieldState(this.titleMessage, this.descriptionMessage);

  @override
  List<Object> get props => [titleMessage ?? "", descriptionMessage ?? ""];
}
