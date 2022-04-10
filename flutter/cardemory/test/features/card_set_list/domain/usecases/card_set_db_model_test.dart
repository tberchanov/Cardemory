import 'package:cardemory/features/card_set_list/data/card_set_db_model.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  test('should create card set db model', () {
    late CardSet cardSet = CardSet(id: 42, name: "Stub Card Set");
    final CardSetDbModel dbModel = CardSetDbModel.fromEntity(cardSet);
    final expectedMap = {
      'id': 42,
      'name': "Stub Card Set",
    };

    final map = dbModel.toMap();

    expect(map, expectedMap);
  });

  test('should create card set db model without id', () {
    late CardSet cardSet = CardSet(name: "Stub Card Set");
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
    late CardSetDbModel expectedCardSet = CardSetDbModel(42, "Stub Card Set");

    final CardSetDbModel dbModel = CardSetDbModel.fromMap(expectedMap);

    expect(dbModel, expectedCardSet);
  });
}
