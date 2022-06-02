import 'package:cardemory/features/cards_list/domain/usecases/get_card_set.dart';
import 'package:cardemory/features/cards_list/presentation/bloc/cards_list_event.dart';
import 'package:cardemory/features/cards_list/presentation/bloc/cards_list_state.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class CardsListBloc extends Bloc<CardsListEvent, CardsListState> {
  static final _log = Logger('CardsListBloc');

  final GetCardSet _getCardSet;

  CardsListBloc(this._getCardSet) : super(CardsListState.initial) {
    on<CardsListEvent>((event, emit) async {
      _log.info("Event: $event");
      await for (final state in _mapEventToState(event)) {
        emit.call(state);
      }
    });
  }

  Stream<CardsListState> _mapEventToState(CardsListEvent event) async* {
    if (event is LoadName) {
      yield* _onCardSetNameLoad(event);
    }
  }

  Stream<CardsListState> _onCardSetNameLoad(LoadName event) async* {
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
