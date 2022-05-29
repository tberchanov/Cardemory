import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/create_card_set/domain/usecases/save_card_set.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_event.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class CreateCardSetBloc extends Bloc<CreateCardSetEvent, CreateCardSetState> {
  final _log = Logger('CreateCardSetBloc');
  final SaveCardSet _saveCardSet;
  final NavBloc _navBloc;

  CreateCardSetBloc(this._saveCardSet, this._navBloc) : super(CreateCardSetInitial()) {
    on<CreateCardSetEvent>((event, emit) async {
      _log.info("Event: $event");
      await for (final state in _mapEventToState(event)) {
        emit.call(state);
      }
    });
  }

  Stream<CreateCardSetState> _mapEventToState(CreateCardSetEvent event) async* {
    if (event is CardSetCreate) {
      yield* _onCardSetSave(event);
    }
  }

  Stream<CreateCardSetState> _onCardSetSave(CardSetCreate event) async* {
    FocusManager.instance.primaryFocus?.unfocus();
    yield CreateCardSetLoading();
    final result = await _saveCardSet(CardSet.name(event.name));

    final state = result.fold((failure) {
      _log.warning("saveCardSet failure: $failure");
      return CreateCardSetError();
    }, (cardSet) {
      _navBloc.add(PopPage());
      return null;
    });

    if (state != null) {
      yield state;
    }
  }
}