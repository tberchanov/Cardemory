import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:cardemory/domain/card/entity/card_validation_info.dart';
import 'package:cardemory/domain/card/usecase/save_card.dart';
import 'package:cardemory/domain/card/usecase/validate_card.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_event.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_state.dart';
import 'package:logging/logging.dart';

class CreateCardBloc extends BaseBloc<CreateCardEvent, CreateCardState> {
  static final _log = Logger("CreateCardBloc");
  final NavBloc _navBloc;
  final ValidateCard _validateCard;
  final SaveCard _saveCard;

  CreateCardBloc(this._navBloc, this._validateCard, this._saveCard) : super(CreateCardState.initial);

  @override
  Stream<CreateCardState> mapEventToState(CreateCardEvent event) async* {
    if (event is CreateCardSaveEvent) {
      yield* _handleSaveCardEvent(event);
    }
  }

  Stream<CreateCardState> _handleSaveCardEvent(CreateCardSaveEvent event) async* {
    final card = Card(event.cardSetId, event.title, event.description);

    final validationResult = await _validateCard(card);
    if (validationResult.isLeft()) {
      _log.warning("Validation failure: ${validationResult.leftOrError()}");
    } else {
      final validationInfo = validationResult.rightOrError();
      if (validationInfo.isSuccess) {
        await _saveCard(card);
        _navBloc.add(NavEvent.pop);
      } else {
        yield* _processValidationInfo(validationInfo);
      }
    }
  }

  Stream<CreateCardState> _processValidationInfo(CardValidationInfo validationInfo) async* {
    if (!validationInfo.isSuccess) {
      yield InvalidFieldState(
        validationInfo.titleValidationMessage,
        validationInfo.descriptionValidationMessage,
      );
    }
  }
}
