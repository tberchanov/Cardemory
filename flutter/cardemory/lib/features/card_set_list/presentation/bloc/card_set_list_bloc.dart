import 'package:bloc/bloc.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'dart:developer' as developer;

part 'card_set_list_event.dart';

part 'card_set_list_state.dart';

class CardSetListBloc extends Bloc<CardSetListEvent, CardSetListState> {
  static const LOG_NAME = "CardSetListBloc";
  final GetCardSetList _getCardSetList;

  CardSetListBloc(this._getCardSetList) : super(CardSetListInitial()) {
    on<CardSetListEvent>((event, emit) async {
      developer.log("Event: $event", name: LOG_NAME);
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
      (failure) => CardSetListError(),
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
