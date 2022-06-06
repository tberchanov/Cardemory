import 'package:cardemory/data/card/db/card_db_model/card_table.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

import '../data/card_set/db/card_set_table.dart';

class DB {
  static var _dbPath = "";

  static Future<String> _getCardemoryDbPath() async {
    if (_dbPath.isEmpty) {
      _dbPath = join(await getDatabasesPath(), 'cardemory.db');
    }
    return _dbPath;
  }

  Database? _db;

  Future<Database> create() async {
    _db ??= await openDatabase(
      await _getCardemoryDbPath(),
      version: 1,
      onCreate: (db, version) => Future.wait([
        CardSetTable.create(db),
        CardTable.create(db),
      ]),
    );

    return _db!;
  }

  Future<void> delete() async {
    _db?.close();
    await deleteDatabase(await _getCardemoryDbPath());
    _db = null;
  }
}
