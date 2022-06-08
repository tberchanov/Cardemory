import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/domain/card/usecase/get_cards_list.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_event.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/cards_list/page_cards_list.dart';
import 'package:cardemory/presentation/create_card/page_create_card.dart';
import 'package:cardemory/presentation/training/page_training.dart';
import 'package:logging/logging.dart';

class CardsListBloc extends BaseBloc<CardsListEvent, CardsListState> {
  static final _log = Logger('CardsListBloc');
  static const _minCardsForTraining = 10;

  final GetCardSet _getCardSet;
  final GetCardsList _getCardsList;
  final NavBloc _navBloc;

  CardsListBloc(
    this._getCardSet,
    this._getCardsList,
    this._navBloc,
  ) : super(const CardsListState.initial()) {
    _navBloc.stream.forEach((pages) {
      final page = pages.last;
      if (page is PageCardsList) {
        add(CardsListEvent.loadCards(page.cardSetId));
      }
    });
  }

  @override
  Stream<CardsListState> mapEventToState(CardsListEvent event) async* {
    _log.info("Event: $event");
    if (event is LoadNameEvent) {
      yield* _onCardSetNameLoad(event);
    } else if (event is CreateCardEvent) {
      yield state.copyWith(hideMessages: true);
      _navBloc.add(NavEvent.add(PageCreateCard(event.cardSetId)));
    } else if (event is CardsListLoad) {
      yield* _loadCardsList(event);
    } else if (event is StartTrainingEvent) {
      yield* _onStartTraining();
    } else if (event is TrainingIsNotAvailableShownEvent) {
      yield state.copyWith(trainingIsNotAvailableMessage: "");
    } else if (event is MessagesHiddenEvent) {
      yield state.copyWith(hideMessages: false);
    }
  }

  Stream<CardsListState> _onStartTraining() async* {
    final cardsQuantity = state.cards?.length ?? 0;
    if (cardsQuantity >= _minCardsForTraining) {
      _navBloc.add(NavEvent.add(PageTraining()));
    } else {
      final cardsDistinction = _minCardsForTraining - cardsQuantity;
      final message = cardsDistinction == 1
          ? "Not enough cards for training. $cardsDistinction card more."
          : "Not enough cards for training. $cardsDistinction cards more.";
      yield state.copyWith(trainingIsNotAvailableMessage: message);
    }
  }

  Stream<CardsListState> _onCardSetNameLoad(LoadNameEvent event) async* {
    if (event.initialName != null) {
      yield state.copyWith(cardSetName: event.initialName);
    } else {
      yield* _loadCardSetName(event.cardSetId);
    }
  }

  Stream<CardsListState> _loadCardSetName(int cardSetId) async* {
    final result = await _getCardSet(cardSetId);
    yield result.fold((failure) {
      return state.copyWith(failure: true);
    }, (cardSet) {
      if (cardSet == null) {
        return state.copyWith(cardSetNotFound: true);
      } else {
        return state.copyWith(cardSetName: cardSet.name);
      }
    });
  }

  Stream<CardsListState> _loadCardsList(CardsListLoad event) async* {
    final result = await _getCardsList(event.cardSetId);
    yield result.fold(
      (failure) {
        _log.warning("_loadCardsList failure: $failure");
        return state.copyWith(failure: true);
      },
      (cards) => state.copyWith(
        cards: cards,
        isTrainingAvailable: cards.length >= _minCardsForTraining,
      ),
    );
  }
}
