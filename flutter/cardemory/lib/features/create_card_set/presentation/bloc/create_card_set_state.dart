import 'package:equatable/equatable.dart';

abstract class CreateCardSetState extends Equatable {

  @override
  List<Object> get props => [];
}

class CreateCardSetInitial extends CreateCardSetState {}

class CreateCardSetLoading extends CreateCardSetState {}

class CreateCardSetError extends CreateCardSetState {}