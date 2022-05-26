import 'package:equatable/equatable.dart';

class CardSet extends Equatable {
  static const unknownId = -1;

  final int id;
  final String name;

  const CardSet({this.id = unknownId, required this.name});

  CardSet copyWith({
    int? id,
    String? name,
  }) =>
      CardSet(
        id: id ?? this.id,
        name: name ?? this.name,
      );

  @override
  List<Object?> get props => [id, name];
}
