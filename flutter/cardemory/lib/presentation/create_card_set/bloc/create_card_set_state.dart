import 'package:equatable/equatable.dart';

abstract class CreateCardSetState extends Equatable {
  static CreateCardSetInitial initial = CreateCardSetInitial._();
  static CreateCardSetLoading loading = CreateCardSetLoading._();
  static CreateCardSetError error = CreateCardSetError._();

  @override
  List<Object> get props => [];
}

class CreateCardSetInitial extends CreateCardSetState {
  CreateCardSetInitial._();
}

class CreateCardSetLoading extends CreateCardSetState {
  CreateCardSetLoading._();
}

class CreateCardSetError extends CreateCardSetState {
  CreateCardSetError._();
}