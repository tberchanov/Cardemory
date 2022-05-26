import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:flutter/material.dart';

class CardSetList extends StatelessWidget {
  final List<CardSet> _cardSets;

  const CardSetList(this._cardSets, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemBuilder: (_, index) => _buildCardSetItem(_cardSets[index]),
      itemCount: _cardSets.length,
    );
  }

  Widget _buildCardSetItem(CardSet cardSet) {
    return Text(cardSet.name);
  }
}
