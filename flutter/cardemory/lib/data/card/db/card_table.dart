import 'package:cardemory/data/card_set/db/card_set_table.dart';
import 'package:sqflite/sqlite_api.dart';

class CardTable {
  static const name = 'card';
  static const propertyId = 'id';
  static const propertyCardSetId = 'cardSetId';
  static const propertyTitle = 'title';
  static const propertyDescription = 'description';
  static const propertyMemoryRank = 'memoryRank';
  static const propertyLastTrainingMillis = 'lastTrainingMillis';

  static Future<void> create(Database db) {
    return db.execute(
      'CREATE TABLE IF NOT EXISTS $name('
      '$propertyId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '
      '$propertyCardSetId INTEGER NOT NULL, '
      '$propertyTitle TEXT NOT NULL, '
      '$propertyDescription TEXT NOT NULL, '
      '$propertyMemoryRank REAL NOT NULL, '
      '$propertyLastTrainingMillis INTEGER NOT NULL, '
      'FOREIGN KEY ($propertyCardSetId) REFERENCES ${CardSetTable.name}(${CardSetTable.propertyId})'
      ')',
    );
  }
}
