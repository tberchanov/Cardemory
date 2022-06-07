import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class PageNotFoundContent extends StatelessWidget {
  const PageNotFoundContent({Key? key}) : super(key: key);

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
            const Text(
              "Page Not Found",
              style: TextStyle(
                fontSize: 60
              ),
            ),
            Padding(
              padding: const EdgeInsets.only(top: 24),
              child: FloatingActionButton.large(
                onPressed: () {
                  context.read<NavBloc>().add(NavEvent.resetToInitial);
                },
                child: const Icon(Icons.home),
              ),
            )
          ],
        ),
      ),
    );
  }
}
