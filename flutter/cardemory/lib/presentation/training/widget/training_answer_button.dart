import 'package:flutter/material.dart';

class TrainingAnswerButton extends StatelessWidget {
  final void Function() onClick;
  final String text;
  final Color color;

  const TrainingAnswerButton(this.onClick, this.text, this.color, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
        onPressed: onClick,
        style: ButtonStyle(
          enableFeedback: true,
          overlayColor: MaterialStateProperty.all(color.withAlpha(50)),
        ),
        child: Padding(
          padding: const EdgeInsets.all(10),
          child: Text(
            text.toUpperCase(),
            style: TextStyle(
              color: color,
              fontSize: 20,
              fontWeight: FontWeight.bold,
              letterSpacing: 2,
            ),
          ),
        ));
  }
}
