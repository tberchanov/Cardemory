import 'package:equatable/equatable.dart';

abstract class CreateCardEvent extends Equatable {
  static save(
    int cardSetId,
    String title,
    String description,
  ) =>
      CreateCardSaveEvent(cardSetId, title, description);

  @override
  List<Object> get props => [];
}

class CreateCardSaveEvent extends CreateCardEvent {
  final int cardSetId;
  final String title;
  final String description;

  CreateCardSaveEvent(this.cardSetId, this.title, this.description);

  @override
  List<Object> get props => [title, description, cardSetId];
}
