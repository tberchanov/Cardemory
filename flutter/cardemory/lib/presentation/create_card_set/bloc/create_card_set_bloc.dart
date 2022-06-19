import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/nav_event.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/usecase/save_card_set_use_case.dart';
import 'package:cardemory/domain/card_set/usecase/validate_card_set_use_case.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_event.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_state.dart';
import 'package:flutter/material.dart';
import 'package:injectable/injectable.dart';
import 'package:logging/logging.dart';

@injectable
class CreateCardSetBloc extends BaseBloc<CreateCardSetEvent, CreateCardSetState> {
  static final _log = Logger('CreateCardSetBloc');
  final SaveCardSetUseCase _saveCardSet;
  final NavBloc _navBloc;
  final ValidateCardSetUseCase _validateCardSet;

  CreateCardSetBloc(
    this._saveCardSet,
    this._navBloc,
    this._validateCardSet,
  ) : super(CreateCardSetState.initial);

  @override
  Stream<CreateCardSetState> mapEventToState(CreateCardSetEvent event) async* {
    if (event is CardSetCreate) {
      yield* _onCardSetSave(event);
    }
  }

  // TODO refactor, split validation and saving logic
  Stream<CreateCardSetState> _onCardSetSave(CardSetCreate event) async* {
    FocusManager.instance.primaryFocus?.unfocus();
    yield CreateCardSetState.loading;
    final cardSet = CardSet.name(event.name);

    final validationResult = await _validateCardSet(cardSet);
    if (validationResult.isRight()) {
      final validationInfo = validationResult.rightOrError();
      final nameValidationMessage = validationInfo.nameValidationMessage;
      if (!validationInfo.isSuccess && nameValidationMessage != null) {
        yield CardSetValidationErrorState(nameValidationMessage);
        return;
      } else {
        yield CardSetValidationErrorState(null);
      }
    } else {
      final failure = validationResult.leftOrError();
      _log.warning("CardSet validation failure: $failure");
      yield CreateCardSetState.error;
      return;
    }

    final saveResult = await _saveCardSet(cardSet);

    final state = saveResult.fold((failure) {
      _log.warning("saveCardSet failure: $failure");
      return CreateCardSetState.error;
    }, (cardSet) {
      _navBloc.add(PopPage());
      return null;
    });

    if (state != null) {
      yield state;
    }
  }
}
