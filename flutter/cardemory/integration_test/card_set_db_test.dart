import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/data/repositories/card_set_repository_db.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';

// ignore_for_file: avoid_print

void onUnexpectedFailure(Failure failure) {
  print('Unexpected failure: $failure');
  assert(false);
}

void main() {
  late DB db = DB();
  late CardSetRepository repository;

  setUpAll(() async {
    IntegrationTestWidgetsFlutterBinding.ensureInitialized();
    await db.delete();
  });

  setUp(() async {
    db = DB();
    repository = CardSetRepositoryDb.fromDB(db);
  });

  tearDown(() async {
    await db.delete();
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
