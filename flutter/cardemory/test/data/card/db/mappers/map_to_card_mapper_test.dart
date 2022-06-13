import 'package:cardemory/data/card/db/card_table.dart';
import 'package:cardemory/data/card/db/mappers/map_to_card_mapper.dart';
import 'package:cardemory/domain/card/entity/card.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('Map Map<String, dynamic> to Card', () {
    final map = {
      CardTable.propertyId: 333,
      CardTable.propertyCardSetId: 42,
      CardTable.propertyTitle: "title",
      CardTable.propertyDescription: "description",
      CardTable.propertyMemoryRank: 12.21,
      CardTable.propertyLastTrainingMillis: 123,
    };
    const expectedCard = Card(42, "title", "description", 12.21, 123, id: 333);

    final card = MapToCardMapper().map(map);

    expect(card, expectedCard);
  });

  test('Map Map<String, dynamic> to Card, Wrong Card id', () {
    final map = {
      CardTable.propertyId: 333,
      CardTable.propertyCardSetId: 42,
      CardTable.propertyTitle: "title",
      CardTable.propertyDescription: "description",
      CardTable.propertyMemoryRank: 12.21,
      CardTable.propertyLastTrainingMillis: 123,
    };
    const wrongIdCard = Card(333, "title", "description", 12.21, 123, id: 11);

    final card = MapToCardMapper().map(map);

    expect(card != wrongIdCard, true);
  });
}
