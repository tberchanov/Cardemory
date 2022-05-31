part of 'card_set_list_bloc.dart';

abstract class CardSetListEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class CardSetListLoad extends CardSetListEvent {}

class CardSetListCreate extends CardSetListEvent {}

class CardSetClick extends CardSetListEvent {
  final CardSet cardSet;

  CardSetClick(this.cardSet);

  @override
  List<Object> get props => [cardSet];
}
