import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/cards_list/widget/card_item.dart';
import 'package:flutter/material.dart';

class CardsListBody extends StatelessWidget {
  final CardsListState _state;

  const CardsListBody(this._state, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final state = _state;
    if (state is LoadedCardsListState) {
      return ListView.builder(
        itemCount: state.cards.length,
        itemBuilder: (context, index) {
          final card = state.cards[index];
          return CardItem(card, () {
            // TODO navigate to card page;
          });
        },
      );
    } else {
      return Container();
    }
  }
}
