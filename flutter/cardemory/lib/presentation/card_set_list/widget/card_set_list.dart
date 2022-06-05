import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/presentation/card_set_list/bloc/card_set_list_bloc.dart';
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
        return _buildCardSetItem(
          cardSet,
          () => bloc.add(CardSetClick(cardSet)),
        );
      },
      itemCount: _cardSets.length,
    );
  }

  Widget _buildCardSetItem(CardSet cardSet, void Function() onClick) {
    return IntrinsicHeight(
      child: Stack(
        fit: StackFit.passthrough,
        alignment: Alignment.bottomRight,
        children: [
          const Card(
            elevation: 1.5,
            margin: EdgeInsets.only(right: 16, top: 20, left: 20, bottom: 4),
          ),
          Card(
            elevation: 1.5,
            margin:
                const EdgeInsets.only(right: 24, top: 12, left: 12, bottom: 12),
            child: InkWell(
              child: Padding(
                padding: const EdgeInsets.all(10.0),
                child: Text(
                  cardSet.name,
                  style: const TextStyle(fontSize: 18),
                ),
              ),
              onTap: onClick,
            ),
          ),
        ],
      ),
    );
  }
}
