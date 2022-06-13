import 'package:cardemory/domain/card/entity/card.dart';
import 'package:equatable/equatable.dart';

abstract class TrainingEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class TrainingEventKnow extends TrainingEvent {
  final Card card;

  TrainingEventKnow(this.card);

  @override
  List<Object> get props => [card];
}

class TrainingEventForgot extends TrainingEvent {
  final Card card;

  TrainingEventForgot(this.card);

  @override
  List<Object> get props => [card];
}

class TrainingEventFinish extends TrainingEvent {}

class TrainingEventSwipeHintShown extends TrainingEvent {}
