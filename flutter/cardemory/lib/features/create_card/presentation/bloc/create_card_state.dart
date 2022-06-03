import 'package:equatable/equatable.dart';

abstract class CreateCardState extends Equatable {

  static final initial = CreateCardInitialState();

  @override
  List<Object> get props => [];
}

class CreateCardInitialState extends CreateCardState{}