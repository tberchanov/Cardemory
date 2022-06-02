import 'package:equatable/equatable.dart';

abstract class CardsListState extends Equatable {
  static final initial = CardsListStateInitial();
  static final cardSetNotFound = CardSetNotFoundState();
  static final failure = CardsListFailureState();

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
