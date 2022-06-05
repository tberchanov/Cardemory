import 'package:equatable/equatable.dart';

class Card extends Equatable {
  static const unknownId = -1;

  final int id;
  final int cardSetId;
  final String title;
  final String description;

  const Card(this.cardSetId, this.title, this.description, {this.id = unknownId});

  Card copyWith({
    final int? id,
    final int? cardSetId,
    final String? title,
    final String? description,
  }) =>
      Card(
        cardSetId ?? this.cardSetId,
        title ?? this.title,
        description ?? this.description,
        id: id ?? this.id,
      );

  @override
  List<Object> get props => [id, title, description];
}
