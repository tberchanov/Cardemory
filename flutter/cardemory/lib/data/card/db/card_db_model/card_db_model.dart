import 'package:cardemory/data/card/db/card_db_model/card_table.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:equatable/equatable.dart';

class CardDbModel extends Equatable {
  final int? id;
  final int cardSetId;
  final String title;
  final String description;

  @override
  List<Object?> get props => [id, cardSetId, title, description];

  const CardDbModel(this.id, this.cardSetId, this.title, this.description);

  CardDbModel.fromMap(Map<String, dynamic> map)
      : id = map[CardTable.propertyId],
        cardSetId = map[CardTable.propertyCardSetId],
        title = map[CardTable.propertyTitle],
        description = map[CardTable.propertyDescription];

  CardDbModel.fromEntity(Card card)
      : id = card.id == Card.unknownId ? null : card.id,
        cardSetId = card.cardSetId,
        title = card.title,
        description = card.description;

  Card toEntity() => Card(cardSetId, title, description, id: id ?? Card.unknownId);

  Map<String, dynamic> toMap() {
    return {
      if (id != null) CardTable.propertyId: id,
      CardTable.propertyCardSetId: cardSetId,
      CardTable.propertyTitle: title,
      CardTable.propertyDescription: description,
    };
  }
}
