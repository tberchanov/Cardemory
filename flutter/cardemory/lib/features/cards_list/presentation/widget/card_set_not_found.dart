import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CardSetNotFound extends StatelessWidget {
  const CardSetNotFound({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        width: double.maxFinite,
        child: Column(
          children: [
            SizedBox.fromSize(
              size: Size.fromHeight(MediaQuery.of(context).size.height * 0.3),
            ),
            const Padding(
              padding: EdgeInsets.all(24.0),
              child: Text(
                "Card set not found",
                style: TextStyle(fontSize: 35),
              ),
            ),
            FloatingActionButton(
              onPressed: () {
                context.read<NavBloc>().add(NavEvent.pop);
              },
              child: const Icon(Icons.arrow_back),
            )
          ],
        ),
      ),
    );
  }
}
