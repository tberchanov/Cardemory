import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

class TrainingState extends Equatable {
  final int rememberedCardsQuantity;
  final int answeredCardsQuantity;
  final TrainingResultMessage? trainingResultMessage;

  const TrainingState(
    this.rememberedCardsQuantity,
    this.answeredCardsQuantity,
    this.trainingResultMessage,
  );

  const TrainingState.initial()
      : rememberedCardsQuantity = 0,
        answeredCardsQuantity = 0,
        trainingResultMessage = null;

  @override
  List<Object?> get props => [
        rememberedCardsQuantity,
        answeredCardsQuantity,
        trainingResultMessage,
      ];

  TrainingState copyWith({
    int? rememberedCardsQuantity,
    int? answeredCardsQuantity,
    TrainingResultMessage? trainingResultMessage,
  }) {
    return TrainingState(
      rememberedCardsQuantity ?? this.rememberedCardsQuantity,
      answeredCardsQuantity ?? this.answeredCardsQuantity,
      trainingResultMessage ?? this.trainingResultMessage,
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
