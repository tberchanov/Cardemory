import 'package:flutter/material.dart';

import '../bloc/training_state.dart';

class TrainingResultDialog extends StatelessWidget {
  final TrainingResultMessage trainingResultMessage;
  final void Function() onBackToCardsClicked;

  const TrainingResultDialog(
    this.trainingResultMessage,
    this.onBackToCardsClicked, {
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Wrap(
        children: [
          Center(
            child: Column(
              children: [
                Text(
                  trainingResultMessage.title,
                  style: TextStyle(color: trainingResultMessage.color),
                ),
                if (trainingResultMessage.description != null)
                  Text(
                    trainingResultMessage.description!,
                    style: TextStyle(color: trainingResultMessage.color),
                  ),
                ...trainingResultMessage.stars.map((star) => Text(star.name)).toList(),
                OutlinedButton(
                  onPressed: onBackToCardsClicked,
                  style: ButtonStyle(
                    enableFeedback: true,
                    overlayColor: MaterialStateProperty.all(trainingResultMessage.color),
                  ),
                  child: Text(
                    "Back to cards",
                    style: TextStyle(color: trainingResultMessage.color),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
