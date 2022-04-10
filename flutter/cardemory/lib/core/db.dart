import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

import '../features/card_set_list/data/card_set_table.dart';

class DB {

  static var _dbPath = "";

  static Future<String> _getCardemoryDbPath() async {
    if (_dbPath.isEmpty) {
      _dbPath = join(await getDatabasesPath(), 'cardemory.db');
    }
    return _dbPath;
  }

  static Future<Database> create() async {
    return openDatabase(
      await _getCardemoryDbPath(),
      version: 1,
      onCreate: (db, version) {
        return CardSetTable.create(db);
      },
    );
  }

  static Future<void> delete() async {
    deleteDatabase(await _getCardemoryDbPath());
  }
}
