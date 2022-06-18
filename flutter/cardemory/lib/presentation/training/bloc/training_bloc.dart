import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/extension/either_ext.dart';
import 'package:cardemory/domain/training/entity/training_result.dart';
import 'package:cardemory/domain/training/usecase/calculate_training_result_use_case.dart';
import 'package:cardemory/domain/training/usecase/process_forgotten_card_use_case.dart';
import 'package:cardemory/domain/training/usecase/process_remembered_card_use_case.dart';
import 'package:cardemory/presentation/training/bloc/training_event.dart';
import 'package:cardemory/presentation/training/bloc/training_state.dart';
import 'package:flutter/material.dart';
import 'package:injectable/injectable.dart';
import 'package:logging/logging.dart';

@injectable
class TrainingBloc extends BaseBloc<TrainingEvent, TrainingState> {
  static final _log = Logger("TrainingBloc");
  final ProcessRememberedCardUseCase _processRememberedCardUseCase;
  final ProcessForgottenCardUseCase _processForgottenCardUseCase;
  final CalculateTrainingResultUseCase _calculateTrainingResultUseCase;

  TrainingBloc(
    this._processRememberedCardUseCase,
    this._processForgottenCardUseCase,
    this._calculateTrainingResultUseCase,
  ) : super(const TrainingState.initial());

  @override
  Stream<TrainingState> mapEventToState(TrainingEvent event) async* {
    _log.info("Event: $event");
    if (event is TrainingEventKnow) {
      yield* _processTrainingEventKnow(event);
    } else if (event is TrainingEventForgot) {
      yield* _processTrainingEventForgot(event);
    } else if (event is TrainingEventFinish) {
      yield* _processTrainingEventFinish();
    }
  }

  Stream<TrainingState> _processTrainingEventKnow(TrainingEventKnow event) async* {
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
  }

  Stream<TrainingState> _processTrainingEventForgot(TrainingEventForgot event) async* {
    final result = await _processForgottenCardUseCase(event.card);
    if (result.isLeft()) {
      _log.warning("Process forgotten card failure: ${result.leftOrError()}");
    } else {
      _log.info("Forgotten card processed: ${result.rightOrError()}");
      yield state.copyWith(answeredCardsQuantity: state.answeredCardsQuantity + 1);
    }
  }

  Stream<TrainingState> _processTrainingEventFinish() async* {
    final result = await _calculateTrainingResultUseCase(state.rememberedCardsQuantity);
    if (result.isLeft()) {
      _log.warning("calculateTrainingResultUseCase failure: ${result.leftOrError()}");
    } else {
      final trainingResult = result.rightOrError();
      yield state.copyWith(trainingResultMessage: _createTrainingResultMessage(trainingResult));
    }
  }

  TrainingResultMessage _createTrainingResultMessage(TrainingResult trainingResult) {
    return TrainingResultMessage(
      title: _getTrainingResultTitle(trainingResult),
      description: _getTrainingResultDescription(trainingResult),
      stars: _getTrainingResultStars(trainingResult),
      color: _getTrainingResultColor(trainingResult),
    );
  }

  String _getTrainingResultTitle(TrainingResult trainingResult) {
    if (trainingResult.index <= TrainingResult.normal.index) {
      return "Insufficiently";
    } else if (trainingResult.index <= TrainingResult.good.index) {
      return "Good";
    } else if (trainingResult.index <= TrainingResult.max.index) {
      return "Great";
    } else {
      throw Exception("Unexpected trainingResult: $trainingResult");
    }
  }

  String? _getTrainingResultDescription(TrainingResult trainingResult) {
    if (trainingResult.index <= TrainingResult.normal.index) {
      return "You should train more often to have better results!";
    } else {
      return null;
    }
  }

  List<Star> _getTrainingResultStars(TrainingResult trainingResult) {
    switch (trainingResult) {
      case TrainingResult.min:
        return [Star.outline, Star.outline, Star.outline];
      case TrainingResult.veryBad:
        return [Star.half, Star.outline, Star.outline];
      case TrainingResult.bad:
        return [Star.full, Star.outline, Star.outline];
      case TrainingResult.normal:
        return [Star.full, Star.half, Star.outline];
      case TrainingResult.good:
        return [Star.full, Star.full, Star.outline];
      case TrainingResult.veryGood:
        return [Star.full, Star.full, Star.half];
      case TrainingResult.max:
        return [Star.full, Star.full, Star.full];
    }
  }

  Color _getTrainingResultColor(TrainingResult trainingResult) {
    if (trainingResult.index <= TrainingResult.normal.index) {
      return const Color(0xFFF44336);
    } else {
      return const Color(0xFF4CAF50);
    }
  }
}
