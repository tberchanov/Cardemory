import 'package:cardemory/data/card/db/card_db_model/card_table.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart' as db;

import 'card_set/db/card_set_table.dart';

abstract class DatabaseModule {
  static var _dbPath = "";

  static Future<String> getDbPath() async {
    if (_dbPath.isEmpty) {
      _dbPath = join(await db.getDatabasesPath(), 'cardemory.db');
    }
    return _dbPath;
  }

  static Future<db.Database> openDatabase() async {
    return await db.openDatabase(
      await getDbPath(),
      version: 1,
      onCreate: (db, version) => Future.wait([
        CardSetTable.create(db),
        CardTable.create(db),
      ]),
    );
  }
}
