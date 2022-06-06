import 'package:cardemory/domain/card/entity/card.dart';
import 'package:equatable/equatable.dart';

abstract class CardsListState extends Equatable {
  static final initial = CardsListStateInitial();
  static final cardSetNotFound = CardSetNotFoundState();
  static final failure = CardsListFailureState();

  static loaded(List<Card> cards) => LoadedCardsListState(cards);

  static cardSetName(String name) => CardSetName(name);

  @override
  List<Object> get props => [];
}

class CardsListStateInitial extends CardsListState {}

class CardSetName extends CardsListState {
  final String name;

  CardSetName(this.name);
}

class CardSetNotFoundState extends CardsListState {}

class CardsListFailureState extends CardsListState {}

class LoadedCardsListState extends CardsListState {
  final List<Card> cards;

  LoadedCardsListState(this.cards);

  @override
  List<Object> get props => [cards];
}
