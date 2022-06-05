import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:equatable/equatable.dart';

class CardSetDbModel extends Equatable {
  final int? id;
  final String name;

  const CardSetDbModel(this.id, this.name);

  CardSetDbModel.fromEntity(CardSet cardSet)
      : id = cardSet.id == CardSet.unknownId ? null : cardSet.id,
        name = cardSet.name;

  CardSetDbModel.fromMap(Map<String, dynamic> map)
      : id = map[CardSetTable.propertyId],
        name = map[CardSetTable.propertyName];

  Map<String, dynamic> toMap() {
    return {
      if (id != null) CardSetTable.propertyId: id,
      CardSetTable.propertyName: name,
    };
  }

  CardSet toEntity() {
    return CardSet(id: id == null ? CardSet.unknownId : id!, name: name);
  }

  @override
  List<Object?> get props => [id, name];
}
