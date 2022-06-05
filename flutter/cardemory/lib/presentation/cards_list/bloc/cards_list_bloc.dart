import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_event.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_state.dart';
import 'package:cardemory/presentation/create_card/page_create_card.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class CardsListBloc extends Bloc<CardsListEvent, CardsListState> {
  static final _log = Logger('CardsListBloc');

  final GetCardSet _getCardSet;
  final NavBloc _navBloc;

  CardsListBloc(
    this._getCardSet,
    this._navBloc,
  ) : super(CardsListState.initial) {
    on<CardsListEvent>((event, emit) async {
      _log.info("Event: $event");
      await for (final state in _mapEventToState(event)) {
        emit.call(state);
      }
    });
  }

  Stream<CardsListState> _mapEventToState(CardsListEvent event) async* {
    if (event is LoadNameEvent) {
      yield* _onCardSetNameLoad(event);
    } else if (event is CreateCardEvent) {
      _navBloc.add(NavEvent.add(PageCreateCard(event.cardSetId)));
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
}
