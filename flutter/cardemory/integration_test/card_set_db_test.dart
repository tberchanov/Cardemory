import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository_impl.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:sqflite/sqflite.dart';

void onUnexpectedFailure(Failure failure) {
  print('Unexpected failure: $failure');
  assert(false);
}

void main() {
  late Database db;
  late CardSetRepository repository;

  setUpAll(() async {
    IntegrationTestWidgetsFlutterBinding.ensureInitialized();
    await DB.delete();
  });

  setUp(() async {
    db = await DB.create();
    repository = CardSetRepositoryDb(db);
  });

  tearDown(() async {
    await db.close();
    await DB.delete();
  });

  testWidgets('test CardSet db saveCardSet', (WidgetTester tester) async {
    final cardSet1Name = "Stub Card Set1";
    final cardSet2Name = "Stub Card Set2";
    final cardSet1 = CardSet(name: cardSet1Name);
    final cardSet2 = CardSet(name: cardSet2Name);

    final createdCardSet1 = await repository.saveCardSet(cardSet1);
    final createdCardSet2 = await repository.saveCardSet(cardSet2);
    final cardSetsEither = await repository.getCardSets();

    createdCardSet1.fold(onUnexpectedFailure, (cardSet) {
      assert(cardSet.id != CardSet.UNKNOWN_ID);
    });
    createdCardSet2.fold(onUnexpectedFailure, (cardSet) {
      assert(cardSet.id != CardSet.UNKNOWN_ID);
    });
    cardSetsEither.fold(onUnexpectedFailure, (cardSets) {
      expect(cardSets.length, 2);
      assert(cardSets.any((element) => element.name == cardSet1Name));
      assert(cardSets.any((element) => element.name == cardSet2Name));
    });
  });
}
