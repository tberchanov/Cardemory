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
  final String? _cardSetName;

  PageCardsList(this.cardSetId) : _cardSetName = null;

  PageCardsList.fromCardSet(CardSet cardSet)
      : cardSetId = cardSet.id,
        _cardSetName = cardSet.name;

  @override
  String get routeName => "/cards-$cardSetId";

  @override
  Widget buildChild() {
    return BlocRenderer<CardsListBloc, CardsListState>(
      (context, state) {
        if (state.cards == null) {
          context.read<CardsListBloc>().add(CardsListEvent.loadCards(cardSetId));
        }
        context.read<CardsListBloc>().add(CardsListEvent.loadName(cardSetId, _cardSetName));

        if (state.cardSetNotFound) {
          return const CardSetNotFound();
        } else {
          return Scaffold(
            appBar: AppBar(
              title: Text(state.cardSetName ?? cardSetId.toString()),
            ),
            body: CardsListBody(
              state,
              () => context.read<CardsListBloc>().add(CardsListEvent.startTraining),
            ),
            floatingActionButton: FloatingActionButton(
              onPressed: () {
                context.read<CardsListBloc>().add(CardsListEvent.creteCard(cardSetId));
              },
              child: const Icon(Icons.add),
            ),
          );
        }
      },
      onListenState: (context, state) {
        if (state.hideMessages) {
          ScaffoldMessenger.of(context).hideCurrentSnackBar();
          context.read<CardsListBloc>().add(CardsListEvent.messagesHidden);
        }

        final message = state.trainingIsNotAvailableMessage;
        if (message != null && message.isNotEmpty) {
          ScaffoldMessenger.of(context).hideCurrentSnackBar();
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message)),
          );
          context.read<CardsListBloc>().add(CardsListEvent.trainingIsNotAvailableShown);
        }
      },
    );
  }
}
