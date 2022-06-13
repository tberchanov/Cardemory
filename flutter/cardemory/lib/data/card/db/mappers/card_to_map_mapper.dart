import 'package:cardemory/data/card/db/card_table.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:injectable/injectable.dart';

@injectable
class CardToMapMapper {
  Map<String, dynamic> map(Card card) {
    return {
      if (card.id != Card.unknownId) CardTable.propertyId: card.id,
      CardTable.propertyCardSetId: card.cardSetId,
      CardTable.propertyTitle: card.title,
      CardTable.propertyDescription: card.description,
      CardTable.propertyMemoryRank: card.memoryRank,
      CardTable.propertyLastTrainingMillis: card.lastTrainingMillis,
    };
  }
}
