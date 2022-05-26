import 'package:sqflite/sqflite.dart';

class CardSetTable {
  static const name = 'card_set';
  static const propertyId = 'id';
  static const propertyName = 'name';

  static Future<void> create(Database db) {
    return db.execute(
      'CREATE TABLE IF NOT EXISTS $name($propertyId INTEGER PRIMARY KEY AUTOINCREMENT, $propertyName TEXT)',
    );
  }
}
