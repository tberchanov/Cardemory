import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/cards_list/widget/card_item.dart';
import 'package:cardemory/presentation/cards_list/widget/training_button.dart';
import 'package:flutter/material.dart';

class CardsListBody extends StatelessWidget {
  final CardsListState _state;
  final void Function() _onTrainingClicked;

  const CardsListBody(this._state, this._onTrainingClicked, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final cards = _state.cards;
    if (cards != null) {
      return ListView.builder(
        itemCount: cards.length + 1,
        itemBuilder: (context, index) {
          if (index == 0) {
            return TrainingButton(_onTrainingClicked, _state.isTrainingAvailable);
          } else {
            final card = cards[index - 1];
            return CardItem(card, () {
              // TODO navigate to card page;
            });
          }
        },
      );
    } else {
      return Container();
    }
  }
}
