import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/presentation/card_set_list/bloc/card_set_list_bloc.dart';
import 'package:cardemory/presentation/card_set_list/widget/card_set_item.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CardSetList extends StatelessWidget {
  final List<CardSet> _cardSets;

  const CardSetList(this._cardSets, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final bloc = context.read<CardSetListBloc>();
    return ListView.builder(
      itemBuilder: (_, index) {
        final cardSet = _cardSets[index];
        return CardSetItem(
          cardSet,
          () => bloc.add(CardSetClick(cardSet)),
        );
      },
      itemCount: _cardSets.length,
    );
  }
}
