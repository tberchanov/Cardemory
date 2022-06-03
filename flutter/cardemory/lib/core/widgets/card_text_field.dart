import 'package:flutter/material.dart';

class CardTextField extends StatelessWidget {
  final String labelText;
  final TextEditingController controller;

  const CardTextField(this.labelText, this.controller, {Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: TextField(
        autofocus: true,
        controller: controller,
        decoration: InputDecoration(
          border: const OutlineInputBorder(),
          labelText: labelText,
        ),
      ),
    );
  }
}