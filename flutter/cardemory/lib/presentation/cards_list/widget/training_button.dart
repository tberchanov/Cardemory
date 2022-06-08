import 'package:flutter/material.dart';
import 'package:flutter_svg/avd.dart';

class TrainingButton extends StatelessWidget {
  final void Function() _onClick;
  final _borderRadius = const BorderRadius.all(Radius.circular(10));
  final bool _isEnabled;

  const TrainingButton(this._onClick, this._isEnabled, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        vertical: 14,
        horizontal: 24,
      ),
      child: Card(
        elevation: 8,
        shape: RoundedRectangleBorder(borderRadius: _borderRadius),
        color: _getCardColor(context),
        child: InkWell(
          onTap: _onClick,
          borderRadius: _borderRadius,
          child: Padding(
            padding: const EdgeInsets.all(8),
            child: SizedBox(
              width: 60,
              height: 60,
              child: AvdPicture.asset('assets/ic_brain_white.xml'),
            ),
          ),
        ),
      ),
    );
  }

  Color _getCardColor(BuildContext context) {
    final theme = Theme.of(context);
    return _isEnabled ? theme.colorScheme.primary : theme.disabledColor;
  }
}
