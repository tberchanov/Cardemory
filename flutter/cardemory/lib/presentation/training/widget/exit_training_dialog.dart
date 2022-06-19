import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/nav_event.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ExitTrainingDialog extends StatelessWidget {
  const ExitTrainingDialog({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: const Text("Training is not finished"),
      actions: [
        TextButton(
          child: Text("Keep training".toUpperCase()),
          onPressed: () => Navigator.of(context).pop(),
        ),
        TextButton(
          child: Text("Exit".toUpperCase()),
          onPressed: () => context.read<NavBloc>().add(NavEvent.pop),
        ),
      ],
    );
  }
}
