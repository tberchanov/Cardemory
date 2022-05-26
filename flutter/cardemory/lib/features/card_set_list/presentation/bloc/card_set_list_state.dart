part of 'card_set_list_bloc.dart';

abstract class CardSetListState extends Equatable {
  const CardSetListState();
  
  @override
  List<Object> get props => [];
}

class CardSetListInitial extends CardSetListState {}

class CardSetListLoading extends CardSetListState {}

class CardSetListEmpty extends CardSetListState {}

class CardSetListError extends CardSetListState {}

class CardSetList extends CardSetListState {
  final List<CardSet> cardSets;

  const CardSetList(this.cardSets);

  @override
  List<Object> get props => [cardSets];
}
