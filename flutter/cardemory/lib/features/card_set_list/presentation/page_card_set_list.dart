import 'package:cardemory/features/card_set_list/presentation/widget/card_set_list.dart'
    as widget;
import 'package:cardemory/features/card_set_list/presentation/widget/card_set_list_bloc_builder.dart';
import 'package:flutter/material.dart';

import 'bloc/card_set_list_bloc.dart';

class PageCardSetList extends StatelessWidget {
  const PageCardSetList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(body: CardSetListBlocBuilder(_buildCardSetListPage));
  }

  Widget _buildCardSetListPage(CardSetListState state) {
    if (state is CardSetListEmpty) {
      return Text("Empty");
    } else if (state is CardSetList) {
      return widget.CardSetList(state.cardSets);
    } else {
      return Text("stub $state");
    }
  }
}
