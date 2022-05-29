import 'package:bloc/bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:cardemory/features/card_set_list/presentation/page_card_set_list.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

part 'card_set_list_event.dart';

part 'card_set_list_state.dart';

class CardSetListBloc extends Bloc<CardSetListEvent, CardSetListState> {
  final _log = Logger('CardSetListBloc');
  final GetCardSetList _getCardSetList;
  final NavBloc _navBloc;

  CardSetListBloc(this._getCardSetList, this._navBloc) : super(CardSetListInitial()) {
    _navBloc.stream.forEach((pages) {
      if (pages.last is PageCardSetList) {
        add(CardSetListLoad());
      }
    });

    on<CardSetListEvent>((event, emit) async {
      _log.info("Event: $event");
      await for (final state in _mapEventToState(event)) {
        emit.call(state);
      }
    });
    add(CardSetListLoad());
  }

  Stream<CardSetListState> _mapEventToState(CardSetListEvent event) async* {
    if (event is CardSetListLoad) {
      yield* _onCardSetListLoad();
    }
  }

  Stream<CardSetListState> _onCardSetListLoad() async* {
    yield CardSetListLoading();
    final cardSetsEither = await _getCardSetList(NoParams());
    yield cardSetsEither.fold(
      (failure) {
        _log.warning("getCardSetList failure: $failure");
        return CardSetListError();
      },
      (cardSets) {
        if (cardSets.isEmpty) {
          return CardSetListEmpty();
        } else {
          return CardSetList(cardSets);
        }
      },
    );
  }
}
