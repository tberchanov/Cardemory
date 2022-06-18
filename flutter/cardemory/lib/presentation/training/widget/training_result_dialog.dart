import 'package:cardemory/presentation/training/widget/star_icon.dart';
import 'package:flutter/material.dart';

import '../bloc/training_state.dart';

class TrainingResultDialog extends StatelessWidget {
  static const double _dialogPadding = 12;
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
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(8))),
      child: Wrap(
        children: [
          Padding(
            padding: const EdgeInsets.all(_dialogPadding),
            child: Center(
              child: Column(
                children: [
                  Text(
                    trainingResultMessage.title,
                    style: TextStyle(
                      color: trainingResultMessage.color,
                      fontSize: 30,
                    ),
                  ),
                  const SizedBox(height: _dialogPadding),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: trainingResultMessage.stars.map((star) => StarIcon(star)).toList(),
                  ),
                  const SizedBox(height: _dialogPadding),
                  if (trainingResultMessage.description != null)
                    Padding(
                      padding: const EdgeInsets.only(bottom: _dialogPadding),
                      child: Text(
                        trainingResultMessage.description!,
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          color: trainingResultMessage.color,
                          fontSize: 16,
                        ),
                      ),
                    ),
                  OutlinedButton(
                    onPressed: onBackToCardsClicked,
                    style: ButtonStyle(
                      enableFeedback: true,
                      overlayColor: MaterialStateProperty.all(trainingResultMessage.color.withAlpha(50)),
                    ),
                    child: Text(
                      "Back to cards".toUpperCase(),
                      style: TextStyle(
                        color: trainingResultMessage.color,
                        letterSpacing: 1,
                        wordSpacing: 1,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
