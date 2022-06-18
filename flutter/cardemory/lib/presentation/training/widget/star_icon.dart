import 'package:cardemory/presentation/training/bloc/training_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/avd.dart';

class StarIcon extends StatelessWidget {
  final Star star;

  const StarIcon(this.star, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final String starIconPath;
    switch (star) {
      case Star.outline:
        starIconPath = 'assets/star_outline.xml';
        break;
      case Star.half:
        starIconPath = 'assets/star_half.xml';
        break;
      case Star.full:
        starIconPath = 'assets/star.xml';
        break;
    }
    return SizedBox(
      width: 60,
      height: 60,
      child: Opacity(
        opacity: star == Star.outline ? 0.5 : 1,
        child: AvdPicture.asset(starIconPath),
      ),
    );
  }
}
