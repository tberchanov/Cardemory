import 'package:equatable/equatable.dart';

abstract class CreateCardSetEvent extends Equatable {
  static CardSetCreate create(String name) => CardSetCreate._(name);

  @override
  List<Object> get props => [];
}

class CardSetCreate extends CreateCardSetEvent {
  final String name;

  CardSetCreate._(this.name);

  @override
  List<Object> get props => [name];
}
