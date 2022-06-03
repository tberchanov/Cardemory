import 'package:equatable/equatable.dart';

abstract class CreateCardEvent extends Equatable {
  static save(String title, String description) => CreateCardSaveEvent(title, description);

  @override
  List<Object> get props => [];
}

class CreateCardSaveEvent extends CreateCardEvent {
  final String title;
  final String description;

  CreateCardSaveEvent(this.title, this.description);

  @override
  List<Object> get props => [title, description];
}
