import 'package:cardemory/data/database_module.dart';
import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/data/card_set/db/card_set_repository_db.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:sqflite/sqflite.dart';

// ignore_for_file: avoid_print

void onUnexpectedFailure(Failure failure) {
  print('Unexpected failure: $failure');
  assert(false);
}

void main() {
  Database? db;
  late CardSetRepository repository;

  Future<void> deleteDb() async {
    await db?.close();
    await deleteDatabase(await DatabaseModule.getDbPath());
  }

  setUpAll(() async {
    IntegrationTestWidgetsFlutterBinding.ensureInitialized();
    await deleteDb();
  });

  setUp(() async {
    db = await DatabaseModule.openDatabase();
    repository = CardSetRepositoryDb(db!);
  });

  tearDown(() async {
    await deleteDb();
  });

  testWidgets('test CardSet db saveCardSet', (WidgetTester tester) async {
    const cardSet1Name = "Stub Card Set1";
    const cardSet2Name = "Stub Card Set2";
    const cardSet1 = CardSet(name: cardSet1Name);
    const cardSet2 = CardSet(name: cardSet2Name);

    final createdCardSet1 = await repository.saveCardSet(cardSet1);
    final createdCardSet2 = await repository.saveCardSet(cardSet2);
    final cardSetsEither = await repository.getCardSets();

    createdCardSet1.fold(onUnexpectedFailure, (cardSet) {
      assert(cardSet.id != CardSet.unknownId);
    });
    createdCardSet2.fold(onUnexpectedFailure, (cardSet) {
      assert(cardSet.id != CardSet.unknownId);
    });
    cardSetsEither.fold(onUnexpectedFailure, (cardSets) {
      expect(cardSets.length, 2);
      assert(cardSets.any((element) => element.name == cardSet1Name));
      assert(cardSets.any((element) => element.name == cardSet2Name));
    });
  });
}
