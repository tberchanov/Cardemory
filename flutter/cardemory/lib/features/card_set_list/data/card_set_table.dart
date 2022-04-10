import 'package:sqflite/sqflite.dart';

class CardSetTable {
  static const name = 'card_set';
  static const property_id = 'id';
  static const property_name = 'name';

  static Future<void> create(Database db) {
    return db.execute(
      'CREATE TABLE IF NOT EXISTS $name($property_id INTEGER PRIMARY KEY AUTOINCREMENT, $property_name TEXT)',
    );
  }
}
