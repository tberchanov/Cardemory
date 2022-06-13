import 'package:cardemory/data/card/db/card_table.dart';
import 'package:cardemory/data/card/db/mappers/card_to_map_mapper.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('Map Card to Map<String, dynamic> without id', () {
    const card = Card(42, "title", "description", 12.21, 123);
    final expectedMap = {
      CardTable.propertyCardSetId: card.cardSetId,
      CardTable.propertyTitle: card.title,
      CardTable.propertyDescription: card.description,
      CardTable.propertyMemoryRank: card.memoryRank,
      CardTable.propertyLastTrainingMillis: card.lastTrainingMillis,
    };

    final map = CardToMapMapper().map(card);

    expect(map, expectedMap);
  });

  test('Map Card to Map<String, dynamic> with id', () {
    const card = Card(42, "title", "description", 12.21, 123, id: 65);
    final expectedMap = {
      CardTable.propertyId: card.id,
      CardTable.propertyCardSetId: card.cardSetId,
      CardTable.propertyTitle: card.title,
      CardTable.propertyDescription: card.description,
      CardTable.propertyMemoryRank: card.memoryRank,
      CardTable.propertyLastTrainingMillis: card.lastTrainingMillis,
    };

    final map = CardToMapMapper().map(card);

    expect(map, expectedMap);
  });
}