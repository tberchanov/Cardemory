import 'package:cardemory/presentation/training/widget/training_answer_button.dart';
import 'package:flutter/material.dart';

class TrainingButtonsPanel extends StatelessWidget {
  final void Function() onForgotClick;
  final void Function() onKnowClick;

  const TrainingButtonsPanel({Key? key, required this.onForgotClick, required this.onKnowClick}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        const Spacer(flex: 1),
        Expanded(
          flex: 7,
          child: TrainingAnswerButton(
            onForgotClick,
            "Forgot",
            Colors.red,
          ),
        ),
        const Spacer(flex: 1),
        Expanded(
          flex: 7,
          child: TrainingAnswerButton(
            onKnowClick,
            "Know",
            Colors.green,
          ),
        ),
        const Spacer(flex: 1),
      ],
    );
  }
}
