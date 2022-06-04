import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/features/create_card/domain/entities/card.dart';
import 'package:cardemory/features/create_card/domain/entities/card_validation_info.dart';
import 'package:cardemory/features/create_card/domain/usecases/validate_card.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_event.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_state.dart';
import 'package:logging/logging.dart';
import 'package:cardemory/core/extension/either_ext.dart';

class CreateCardBloc extends BaseBloc<CreateCardEvent, CreateCardState> {
  static final _log = Logger("CreateCardBloc");
  final NavBloc _navBloc;
  final ValidateCard _validateCard;

  CreateCardBloc(this._navBloc, this._validateCard) : super(CreateCardState.initial);

  @override
  Stream<CreateCardState> mapEventToState(CreateCardEvent event) async* {
    if (event is CreateCardSaveEvent) {
      yield* _handleSaveCardEvent(event);
    }
  }

  Stream<CreateCardState> _handleSaveCardEvent(CreateCardSaveEvent event) async* {
    final card = Card(event.title, event.description);

    final validationResult = await _validateCard(card);
    if (validationResult.isLeft()) {
      _log.warning("Validation failure: ${validationResult.leftOrError()}");
    } else {
      final validationInfo = validationResult.rightOrError();
      if (validationInfo.isSuccess) {
        yield* _saveCard(card);
      } else {
        yield* _processValidationInfo(validationInfo);
      }
    }
  }

  Stream<CreateCardState> _saveCard(Card card) async* {
    // TODO save card to repository
    _navBloc.add(NavEvent.pop);
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
