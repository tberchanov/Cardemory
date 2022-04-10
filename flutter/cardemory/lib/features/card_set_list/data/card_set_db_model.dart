import 'package:cardemory/features/card_set_list/data/card_set_table.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:equatable/equatable.dart';

class CardSetDbModel extends Equatable {
  final int? id;
  final String name;

  CardSetDbModel(this.id, this.name);

  CardSetDbModel.fromEntity(CardSet cardSet)
      : this.id = cardSet.id == CardSet.UNKNOWN_ID ? null : cardSet.id,
        this.name = cardSet.name;

  CardSetDbModel.fromMap(Map<String, dynamic> map)
      : this.id = map[CardSetTable.property_id],
        this.name = map[CardSetTable.property_name];

  Map<String, dynamic> toMap() {
    return {
      if (id != null) CardSetTable.property_id: id,
      CardSetTable.property_name: name,
    };
  }

  CardSet toEntity() {
    return CardSet(id: id == null ? CardSet.UNKNOWN_ID : id!, name: name);
  }

  @override
  List<Object?> get props => [id, name];
}
