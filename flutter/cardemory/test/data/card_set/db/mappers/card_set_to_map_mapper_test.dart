import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/data/card_set/db/mappers/card_set_to_map_mapper.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('Map CardSet to Map<String, dynamic> without id', () {
    const cardSet = CardSet(name: "name");
    final expectedMap = {
      CardSetTable.propertyName: cardSet.name,
    };

    final map = CardSetToMapMapper().map(cardSet);

    expect(map, expectedMap);
  });

  test('Map CardSet to Map<String, dynamic> with id', () {
    const cardSet = CardSet(name: "name", id: 123);
    final expectedMap = {
      CardSetTable.propertyId: cardSet.id,
      CardSetTable.propertyName: cardSet.name,
    };

    final map = CardSetToMapMapper().map(cardSet);

    expect(map, expectedMap);
  });
}