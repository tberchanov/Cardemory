import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:cardemory/data/card_set/db/mappers/map_to_card_set_mapper.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('Map Map<String, dynamic> to CardSet', () {
    final map = {
      CardSetTable.propertyId: 333,
      CardSetTable.propertyName: "name",
    };
    const expectedCard = CardSet(name: "name", id: 333);

    final card = MapToCardSetMapper().map(map);

    expect(card, expectedCard);
  });

  test('Map Map<String, dynamic> to CardSet, Wrong CardSet id', () {
    final map = {
      CardSetTable.propertyId: 333,
      CardSetTable.propertyName: "name",
    };
    const wrongIdCard = CardSet(name: "name", id: 111);

    final card = MapToCardSetMapper().map(map);

    expect(card != wrongIdCard, true);
  });

  test('Map Map<String, dynamic> to CardSet, Wrong CardSet name', () {
    final map = {
      CardSetTable.propertyId: 333,
      CardSetTable.propertyName: "name",
    };
    const wrongNameCard = CardSet(name: "another name", id: 333);

    final card = MapToCardSetMapper().map(map);

    expect(card != wrongNameCard, true);
  });
}