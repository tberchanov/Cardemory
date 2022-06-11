import 'package:cardemory/domain/card/entity/card.dart';

abstract class TrainingEvent {}

class TrainingEventKnow extends TrainingEvent {
  final Card card;

  TrainingEventKnow(this.card);
}

class TrainingEventForgot extends TrainingEvent {
  final Card card;

  TrainingEventForgot(this.card);
}

class TrainingEventFinish extends TrainingEvent {}

class TrainingEventSwipeHintShown extends TrainingEvent {}