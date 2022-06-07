import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/domain/card/usecase/get_cards_list.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_event.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/cards_list/page_cards_list.dart';
import 'package:cardemory/presentation/create_card/page_create_card.dart';
import 'package:logging/logging.dart';

class CardsListBloc extends BaseBloc<CardsListEvent, CardsListState> {
  static final _log = Logger('CardsListBloc');

  final GetCardSet _getCardSet;
  final GetCardsList _getCardsList;
  final NavBloc _navBloc;

  CardsListBloc(this._getCardSet, this._getCardsList, this._navBloc) : super(CardsListState.initial) {
    _navBloc.stream.forEach((pages) {
      final page = pages.last;
      if (page is PageCardsList) {
        add(CardsListEvent.loadCards(page.cardSetId));
      }
    });
  }

  @override
  Stream<CardsListState> mapEventToState(CardsListEvent event) async* {
    if (event is LoadNameEvent) {
      yield* _onCardSetNameLoad(event);
    } else if (event is CreateCardEvent) {
      _navBloc.add(NavEvent.add(PageCreateCard(event.cardSetId)));
    } else if (event is CardsListLoad) {
      yield* _loadCardsList(event);
    }
  }

  Stream<CardsListState> _onCardSetNameLoad(LoadNameEvent event) async* {
    final result = await _getCardSet(event.cardSetId);
    yield result.fold((failure) {
      return CardsListState.failure;
    }, (cardSet) {
      if (cardSet == null) {
        return CardsListState.cardSetNotFound;
      } else {
        return CardsListState.cardSetName(cardSet.name);
      }
    });
  }

  Stream<CardsListState> _loadCardsList(CardsListLoad event) async* {
    final result = await _getCardsList(event.cardSetId);
    yield result.fold((failure) {
      _log.warning("_loadCardsList failure: $failure");
      return CardsListState.failure;
    }, (cards) => CardsListState.loaded(cards));
  }
}
