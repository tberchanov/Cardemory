import 'package:equatable/equatable.dart';

class CardSet extends Equatable {
  static const UNKNOWN_ID = -1;

  final int id;
  final String name;

  CardSet({this.id = UNKNOWN_ID, required this.name});

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
