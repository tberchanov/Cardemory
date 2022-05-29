import 'package:equatable/equatable.dart';

abstract class CreateCardSetEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class CardSetCreate extends CreateCardSetEvent {
  final String name;

  CardSetCreate(this.name);

  @override
  List<Object> get props => [name];
}
