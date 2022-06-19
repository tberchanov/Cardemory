import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

class TrainingState extends Equatable {
  final int rememberedCardsQuantity;
  final int answeredCardsQuantity;
  final TrainingResultMessage? trainingResultMessage;
  final bool showExitMessage;

  const TrainingState(
    this.rememberedCardsQuantity,
    this.answeredCardsQuantity,
    this.trainingResultMessage,
    this.showExitMessage,
  );

  const TrainingState.initial()
      : rememberedCardsQuantity = 0,
        answeredCardsQuantity = 0,
        trainingResultMessage = null,
        showExitMessage = false;

  @override
  List<Object?> get props => [
        rememberedCardsQuantity,
        answeredCardsQuantity,
        trainingResultMessage,
        showExitMessage,
      ];

  TrainingState copyWith({
    int? rememberedCardsQuantity,
    int? answeredCardsQuantity,
    TrainingResultMessage? trainingResultMessage,
    bool? showExitMessage,
  }) {
    return TrainingState(
      rememberedCardsQuantity ?? this.rememberedCardsQuantity,
      answeredCardsQuantity ?? this.answeredCardsQuantity,
      trainingResultMessage ?? this.trainingResultMessage,
      showExitMessage ?? this.showExitMessage,
    );
  }
}

class TrainingResultMessage extends Equatable {
  final String title;
  final String? description;
  final Color color;
  final List<Star> stars;

  const TrainingResultMessage({
    required this.title,
    required this.description,
    required this.color,
    required this.stars,
  });

  @override
  List<Object?> get props => [title, description ?? "", color, stars];
}

enum Star { full, half, outline }
