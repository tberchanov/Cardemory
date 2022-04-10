part of 'card_set_list_bloc.dart';

abstract class CardSetListEvent extends Equatable {
  const CardSetListEvent();

  @override
  List<Object> get props => [];
}

class CardSetListLoad extends CardSetListEvent {}
class CardSetListCreate extends CardSetListEvent {}
