import 'package:flutter/material.dart';
import 'package:cardemory/domain/card/entity/card.dart' as entity;

class CardItem extends StatelessWidget {
  final entity.Card card;
  final void Function() onClick;

  const CardItem(this.card, this.onClick, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
      child: Card(
        elevation: 1.5,
        child: InkWell(
          child: Padding(
            padding: const EdgeInsets.all(10.0),
            child: Text(
              card.title,
              style: const TextStyle(fontSize: 18),
            ),
          ),
          onTap: onClick,
        ),
      ),
    );
  }
}
