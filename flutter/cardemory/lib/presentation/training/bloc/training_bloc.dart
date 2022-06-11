import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/presentation/training/bloc/training_event.dart';
import 'package:cardemory/presentation/training/bloc/training_state.dart';

class TrainingBloc extends BaseBloc<TrainingEvent, TrainingState> {
  TrainingBloc() : super(const TrainingState.initial());

  @override
  Stream<TrainingState> mapEventToState(TrainingEvent event) async* {
    if (event is TrainingEventKnow) {
      // todo process card
    } else if (event is TrainingEventForgot) {
      // todo process card
    } else if (event is TrainingEventFinish) {
      // todo show message
    }
  }
}
