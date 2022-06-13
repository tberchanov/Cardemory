import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/domain/training/usecase/process_forgotten_card_use_case.dart';
import 'package:cardemory/domain/training/usecase/process_remembered_card_use_case.dart';
import 'package:cardemory/presentation/training/bloc/training_event.dart';
import 'package:cardemory/presentation/training/bloc/training_state.dart';
import 'package:injectable/injectable.dart';
import 'package:logging/logging.dart';

@injectable
class TrainingBloc extends BaseBloc<TrainingEvent, TrainingState> {
  static final _log = Logger("TrainingBloc");
  final ProcessRememberedCardUseCase _processRememberedCardUseCase;
  final ProcessForgottenCardUseCase _processForgottenCardUseCase;

  TrainingBloc(
    this._processRememberedCardUseCase,
    this._processForgottenCardUseCase,
  ) : super(const TrainingState.initial());

  @override
  Stream<TrainingState> mapEventToState(TrainingEvent event) async* {
    _log.info("Event: $event");
    if (event is TrainingEventKnow) {
      final result = await _processRememberedCardUseCase(event.card);
      if (result.isLeft()) {
        _log.warning("Process remembered card failure: ${result.leftOrError()}");
      } else {
        _log.info("Remembered card processed: ${result.rightOrError()}");
        yield state.copyWith(
          rememberedCardsQuantity: state.rememberedCardsQuantity + 1,
          answeredCardsQuantity: state.answeredCardsQuantity + 1,
        );
      }
    } else if (event is TrainingEventForgot) {
      final result = await _processForgottenCardUseCase(event.card);
      if (result.isLeft()) {
        _log.warning("Process forgotten card failure: ${result.leftOrError()}");
      } else {
        _log.info("Forgotten card processed: ${result.rightOrError()}");
        yield state.copyWith(answeredCardsQuantity: state.answeredCardsQuantity + 1);
      }
    } else if (event is TrainingEventFinish) {
      yield state.copyWith(showFinishedTrainingMessage: true);
    }
  }
}
