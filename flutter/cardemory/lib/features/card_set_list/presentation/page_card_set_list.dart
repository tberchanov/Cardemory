import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/features/card_set_list/presentation/widget/card_set_list.dart'
    as widget;
import 'package:cardemory/features/create_card_set/presentation/page_create_card_set.dart';
import 'package:cardemory/injection_container.dart' as di;
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

import 'bloc/card_set_list_bloc.dart';

final _log = Logger('PageCardSetList');

class PageCardSetList extends AppPage {
  @override
  String get routeName => "/";

  @override
  Widget buildChild() {
    return Scaffold(
      appBar: AppBar(title: const Text("Cardemory")),
      body: BlocRenderer<CardSetListBloc, CardSetListState>(
        (state, _) => CardSetListContent(state),
      ),
      floatingActionButton: FloatingActionButton(
        child: const Icon(Icons.add),
        onPressed: () {
          _log.info("onPressed add");
          di.getIt.get<NavBloc>().add(AddPage(PageCreateCardSet()));
        },
      ),
    );
  }
}

class CardSetListContent extends StatelessWidget {
  final CardSetListState _state;

  const CardSetListContent(this._state, {Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    _log.info("State: $_state");
    return _buildCardSetListContent(_state);
  }

  Widget _buildCardSetListContent(CardSetListState state) {
    if (state is CardSetListEmpty) {
      return const Text("Empty");
    } else if (state is CardSetList) {
      return widget.CardSetList(state.cardSets);
    } else {
      return Text("stub $state");
    }
  }
}
