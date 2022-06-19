import 'package:cardemory/presentation/training/widget/star_icon.dart';
import 'package:flutter/foundation.dart';
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
    return WillPopScope(
      onWillPop: () => SynchronousFuture(false),
      child: Dialog(
        shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(8))),
        child: Wrap(
          children: [
            Padding(
              padding: const EdgeInsets.all(_dialogPadding),
              child: Center(
                child: Column(
                  children: [
                    _DialogTitle(trainingResultMessage.title, trainingResultMessage.color),
                    const SizedBox(height: _dialogPadding),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: trainingResultMessage.stars.map((star) => StarIcon(star)).toList(),
                    ),
                    const SizedBox(height: _dialogPadding),
                    if (trainingResultMessage.description != null)
                      Padding(
                        padding: const EdgeInsets.only(bottom: _dialogPadding),
                        child: _DialogDescription(trainingResultMessage.description!, trainingResultMessage.color),
                      ),
                    _DialogButton(onClick: onBackToCardsClicked, color: trainingResultMessage.color),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _DialogTitle extends StatelessWidget {
  final String title;
  final Color color;

  const _DialogTitle(this.title, this.color, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      title,
      style: TextStyle(
        color: color,
        fontSize: 30,
      ),
    );
  }
}

class _DialogDescription extends StatelessWidget {
  final String description;
  final Color color;

  const _DialogDescription(this.description, this.color, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Text(
      description,
      textAlign: TextAlign.center,
      style: TextStyle(
        color: color,
        fontSize: 16,
      ),
    );
  }
}

class _DialogButton extends StatelessWidget {
  final void Function() onClick;
  final Color color;

  const _DialogButton({Key? key, required this.onClick, required this.color}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      onPressed: onClick,
      style: ButtonStyle(
        enableFeedback: true,
        overlayColor: MaterialStateProperty.all(color.withAlpha(50)),
      ),
      child: Text(
        "Back to cards".toUpperCase(),
        style: TextStyle(
          color: color,
          letterSpacing: 1,
          wordSpacing: 1,
        ),
      ),
    );
  }
}
