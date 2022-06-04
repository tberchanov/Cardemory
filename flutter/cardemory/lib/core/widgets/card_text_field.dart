import 'package:flutter/material.dart';

class CardTextField extends StatelessWidget {
  final String labelText;
  final TextEditingController controller;
  final String? errorText;

  const CardTextField(this.labelText, this.controller, {Key? key, this.errorText}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: TextField(
        autofocus: true,
        controller: controller,
        decoration: InputDecoration(
          errorText: errorText,
          border: const OutlineInputBorder(),
          labelText: labelText,
        ),
      ),
    );
  }
}
