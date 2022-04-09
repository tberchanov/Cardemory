import 'package:equatable/equatable.dart';
import 'card.dart';

class CardSet extends Equatable {
  final int id;
  final String name;
  final List<Card> cards;

  CardSet(this.id, this.name, this.cards);

  @override
  List<Object?> get props => [id, name, cards];
}