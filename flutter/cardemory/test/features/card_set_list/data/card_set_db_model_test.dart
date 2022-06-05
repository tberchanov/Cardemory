import 'package:cardemory/data/card_set/db/card_set_db_model.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('should create card set db model', () {
    late CardSet cardSet = const CardSet(id: 42, name: "Stub Card Set");
    final CardSetDbModel dbModel = CardSetDbModel.fromEntity(cardSet);
    final expectedMap = {
      'id': 42,
      'name': "Stub Card Set",
    };

    final map = dbModel.toMap();

    expect(map, expectedMap);
  });

  test('should create card set db model without id', () {
    late CardSet cardSet = const CardSet(name: "Stub Card Set");
    final CardSetDbModel dbModel = CardSetDbModel.fromEntity(cardSet);
    final expectedMap = {'name': "Stub Card Set"};

    final map = dbModel.toMap();

    expect(map, expectedMap);
  });

  test('should create card set db model from map', () {
    final expectedMap = {
      'id': 42,
      'name': "Stub Card Set",
    };
    const expectedCardSet = CardSetDbModel(42, "Stub Card Set");

    final dbModel = CardSetDbModel.fromMap(expectedMap);

    expect(dbModel, expectedCardSet);
  });
}
