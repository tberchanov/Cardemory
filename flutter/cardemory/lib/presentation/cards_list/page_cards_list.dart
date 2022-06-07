import 'package:cardemory/core/extension/string_ext.dart';
import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_bloc.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_event.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/cards_list/widget/card_set_not_found.dart';
import 'package:cardemory/presentation/cards_list/widget/cards_list_body.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class PageCardsListFactory extends AppPageFactory {
  static final RegExp _regExp = RegExp(r'cards-.+');
  static final _log = Logger("PageCardsListFactory");

  @override
  AppPage? build(RouteData routeData) {
    final route = routeData.route;
    if (_regExp.hasMatch(route)) {
      final cardSetId = route.substringAfter('-')?.tryInt();
      if (cardSetId != null) {
        return PageCardsList(cardSetId);
      }
      _log.warning("Cannot parse: $routeData");
    }
    return null;
  }
}

class PageCardsList extends AppPage {
  final int cardSetId;
  String? _cardSetName;

  PageCardsList(this.cardSetId) : _cardSetName = null;

  PageCardsList.fromCardSet(CardSet cardSet)
      : cardSetId = cardSet.id,
        _cardSetName = cardSet.name;

  @override
  String get routeName => "/cards-$cardSetId";

  @override
  Widget buildChild() {
    return BlocRenderer<CardsListBloc, CardsListState>((state, context) {
      if (state is CardsListStateInitial) {
        context.read<CardsListBloc>().add(CardsListEvent.loadCards(cardSetId));
      }

      if (state is CardSetName) {
        _cardSetName = state.name;
      }

      if (_cardSetName == null) {
        context.read<CardsListBloc>().add(CardsListEvent.loadName(cardSetId));
      }

      if (state is CardSetNotFoundState) {
        return const CardSetNotFound();
      } else {
        return Scaffold(
          appBar: AppBar(
            title: Text(_cardSetName ?? cardSetId.toString()),
          ),
          body: CardsListBody(state),
          floatingActionButton: FloatingActionButton(
            onPressed: () {
              context.read<CardsListBloc>().add(CardsListEvent.creteCard(cardSetId));
            },
            child: const Icon(Icons.add),
          ),
        );
      }
    });
  }
}
