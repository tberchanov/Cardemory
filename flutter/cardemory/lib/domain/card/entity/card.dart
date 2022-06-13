import 'package:equatable/equatable.dart';

class Card extends Equatable {
  static const unknownId = -1;

  final int id;
  final int cardSetId;
  final String title;
  final String description;
  final double memoryRank;
  final int lastTrainingMillis;

  const Card(
    this.cardSetId,
    this.title,
    this.description,
    this.memoryRank,
    this.lastTrainingMillis, {
    this.id = unknownId,
  });

  Card copyWith({
    final int? id,
    final int? cardSetId,
    final String? title,
    final String? description,
    final double? memoryRank,
    final int? lastTrainingMillis,
  }) =>
      Card(
        cardSetId ?? this.cardSetId,
        title ?? this.title,
        description ?? this.description,
        memoryRank ?? this.memoryRank,
        lastTrainingMillis ?? this.lastTrainingMillis,
        id: id ?? this.id,
      );

  @override
  List<Object> get props => [id, title, description];
}
