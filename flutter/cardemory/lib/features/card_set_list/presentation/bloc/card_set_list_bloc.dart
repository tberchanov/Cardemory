import 'package:bloc/bloc.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

part 'card_set_list_event.dart';

part 'card_set_list_state.dart';

class CardSetListBloc extends Bloc<CardSetListEvent, CardSetListState> {
  final GetCardSetList _getCardSetList;

  CardSetListBloc(this._getCardSetList) : super(CardSetListInitial()) {
    on<CardSetListEvent>((event, emit) async {
      if (event is CardSetListLoad || state is CardSetListInitial) {
        await _onCardSetListLoad(emit);
      }
    });
  }

  Future<void> _onCardSetListLoad(Emitter<CardSetListState> emit) async {
    emit.call(CardSetListLoading());
    final cardSetsEither = await _getCardSetList(NoParams());
    cardSetsEither.fold(
      (failure) => _emitError(emit),
      (cardSets) {
        if (cardSets.isEmpty) {
          emit.call(CardSetListEmpty());
        } else {
          emit.call(CardSetList(cardSets));
        }
      },
    );
  }

  void _emitError(Emitter<CardSetListState> emit) {
    // TODO log error
    emit.call(CardSetListError());
  }
}
