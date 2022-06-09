import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:equatable/equatable.dart';

class TrainingData extends Equatable {
  final CardSet cardSet;
  final List<Card> cards;

  const TrainingData(this.cardSet, this.cards);

  @override
  List<Object?> get props => [cardSet, cards];
}
