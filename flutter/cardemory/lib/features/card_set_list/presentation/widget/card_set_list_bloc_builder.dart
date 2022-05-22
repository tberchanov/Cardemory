import 'dart:developer' as developer;

import 'package:cardemory/injection_container.dart' as di;
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../bloc/card_set_list_bloc.dart';

class CardSetListBlocBuilder extends StatelessWidget {
  final Widget Function(CardSetListState) _renderPage;

  CardSetListBlocBuilder(this._renderPage);

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<CardSetListBloc>(
      future: di.getIt.getAsync<CardSetListBloc>(),
      builder: (_, asyncSnapshot) {
        if (asyncSnapshot.hasData) {
          return BlocProvider(
            create: (_) => asyncSnapshot.data!,
            child: BlocBuilder<CardSetListBloc, CardSetListState>(
              builder: (context, state) => _renderPage(state),
            ),
          );
        } else {
          // TODO figure out why CardSetListBloc is not available
          developer.log("CardSetListBloc is not available", name: "CardSetListBlocBuilder");
          // throw FailureWithMessage("CardSetListBloc is not available!");
          return Center(
            child: Text("CardSetListBloc is not available"),
          );
        }
      },
    );
  }
}
