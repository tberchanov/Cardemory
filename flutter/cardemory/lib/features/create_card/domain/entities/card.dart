import 'package:equatable/equatable.dart';

class Card extends Equatable {
  static const unknownId = -1;

  final int id;
  final String title;
  final String description;

  const Card(this.title, this.description, {this.id = unknownId});

  @override
  List<Object> get props => [id, title, description];
}
