import 'dart:developer' as developer;

import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/features/card_set_list/presentation/widget/card_set_list.dart'
    as widget;
import 'package:cardemory/features/create_card_set/presentation/page_create_card_set.dart';
import 'package:flutter/material.dart';

import 'bloc/card_set_list_bloc.dart';
import 'package:cardemory/injection_container.dart' as di;

const _LOG_NAME = "PageCardSetList";

class PageCardSetList extends AppPage {
  PageCardSetList()
      : super(
          Scaffold(
            appBar: AppBar(),
            body: BlocRenderer<CardSetListBloc, CardSetListState>(
              (state) => CardSetListContent(state),
            ),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                developer.log("FAB onPressed", name: "PageCardSetList");
                di.getIt.get<NavBloc>().add(AddPage(PageCreateCardSet()));
              },
            ),
          ),
        );

  @override
  String get routeName => "/";
}

class CardSetListContent extends StatelessWidget {
  final CardSetListState _state;

  const CardSetListContent(this._state);

  @override
  Widget build(BuildContext context) {
    developer.log("State: $_state", name: _LOG_NAME);
    return _buildCardSetListContent(_state);
  }

  Widget _buildCardSetListContent(CardSetListState state) {
    if (state is CardSetListEmpty) {
      return Text("Empty");
    } else if (state is CardSetList) {
      return widget.CardSetList(state.cardSets);
    } else {
      return Text("stub $state");
    }
  }
}
