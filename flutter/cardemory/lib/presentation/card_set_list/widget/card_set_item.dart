import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:flutter/material.dart';

class CardSetItem extends StatelessWidget {
  final CardSet cardSet;
  final void Function() onClick;

  const CardSetItem(this.cardSet, this.onClick, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
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
            margin: const EdgeInsets.only(right: 24, top: 12, left: 12, bottom: 12),
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