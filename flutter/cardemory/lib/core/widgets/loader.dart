import 'package:flutter/material.dart';
import 'package:flutter_spinkit/flutter_spinkit.dart';

class Loader extends StatelessWidget {
  const Loader({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.black26,
      width: double.maxFinite,
      height: double.maxFinite,
      child: const SpinKitSquareCircle(
        color: Colors.lightGreen,
        size: 70,
      ),
    );
  }
}
