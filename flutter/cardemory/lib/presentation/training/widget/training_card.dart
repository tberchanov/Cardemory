import 'package:flutter/material.dart';

class TrainingCard extends StatelessWidget {
  final String text;
  final void Function() onTap;

  const TrainingCard({Key? key, required this.text, required this.onTap}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 10,
      shape: const RoundedRectangleBorder(borderRadius: BorderRadius.all(Radius.circular(15))),
      margin: const EdgeInsets.only(top: 50, left: 40, right: 40),
      child: InkWell(
        onTap: onTap,
        child: Center(
          child: Text(
            text,
            style: const TextStyle(fontSize: 30),
          ),
        ),
      ),
    );
  }
}
