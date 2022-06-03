import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/cards_list/presentation/bloc/cards_list_bloc.dart';
import 'package:cardemory/features/cards_list/presentation/bloc/cards_list_event.dart';
import 'package:cardemory/features/cards_list/presentation/bloc/cards_list_state.dart';
import 'package:cardemory/features/cards_list/presentation/widget/card_set_not_found.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class PageCardsListFactory extends AppPageFactory {
  static final RegExp _regExp = RegExp(r'cards-.+');
  static const _prefixLength = 6; // "cards-"
  static final _log = Logger("PageCardsListFactory");

  @override
  AppPage? build(String route) {
    if (_regExp.hasMatch(route)) {
      final cardSetId = int.tryParse(route.substring(_prefixLength));
      _log.info("build, id: $cardSetId");
      if (cardSetId != null) {
        return PageCardsList(cardSetId);
      }
    }
    return null;
  }
}

class PageCardsList extends AppPage {
  final int _cardSetId;
  String? _cardSetName;

  PageCardsList(this._cardSetId) : _cardSetName = null;

  PageCardsList.fromCardSet(CardSet cardSet)
      : _cardSetId = cardSet.id,
        _cardSetName = cardSet.name;

  @override
  String get routeName => "/cards-$_cardSetId";

  @override
  Widget buildChild() {
    return BlocRenderer<CardsListBloc, CardsListState>((state, context) {
      if (state is CardSetName) {
        _cardSetName = state.name;
      }

      if (_cardSetName == null) {
        context.read<CardsListBloc>().add(CardsListEvent.loadName(_cardSetId));
      }

      if (state is CardSetNotFoundState) {
        return const CardSetNotFound();
      } else {
        return Scaffold(
          appBar: AppBar(
            title: Text(_cardSetName ?? _cardSetId.toString()),
          ),
          floatingActionButton: FloatingActionButton(
            onPressed: () {
              context.read<CardsListBloc>().add(CardsListEvent.creteCard);
            },
            child: const Icon(Icons.add),
          ),
        );
      }
    });
  }
}
