import 'package:equatable/equatable.dart';

class TrainingState extends Equatable {
  final int rememberedCardsQuantity;
  final int answeredCardsQuantity;
  final bool showFinishedTrainingMessage;

  const TrainingState(this.rememberedCardsQuantity, this.answeredCardsQuantity, this.showFinishedTrainingMessage);

  const TrainingState.initial()
      : rememberedCardsQuantity = 0,
        answeredCardsQuantity = 0,
        showFinishedTrainingMessage = false;

  @override
  List<Object?> get props => [rememberedCardsQuantity];

  TrainingState copyWith({
    int? rememberedCardsQuantity,
    int? answeredCardsQuantity,
    bool? showFinishedTrainingMessage,
  }) {
    return TrainingState(
      rememberedCardsQuantity ?? this.rememberedCardsQuantity,
      answeredCardsQuantity ?? this.answeredCardsQuantity,
      showFinishedTrainingMessage ?? this.showFinishedTrainingMessage,
    );
  }
}
