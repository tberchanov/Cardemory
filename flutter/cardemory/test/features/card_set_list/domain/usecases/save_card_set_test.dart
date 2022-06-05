import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:cardemory/domain/card_set/usecase/save_card_set.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'save_card_set_test.mocks.dart';

@GenerateMocks([CardSetRepository])
void main() {
  late SaveCardSet usecase;
  late MockCardSetRepository mockCardSetRepository;

  setUp(() {
    mockCardSetRepository = MockCardSetRepository();
    usecase = SaveCardSet(mockCardSetRepository);
  });

  const CardSet cardSetParam = CardSet(id: 1, name: "Param Stub");
  const CardSet cardSetResult = CardSet(id: -1, name: "Result Stub");

  test('should save card set to repository', () async {
    when(mockCardSetRepository.saveCardSet(cardSetParam))
        .thenAnswer((realInvocation) async => const Right(cardSetResult));

    final result = await usecase(cardSetParam);

    expect(result, const Right(cardSetResult));
    verify(mockCardSetRepository.saveCardSet(cardSetParam));
    verifyNoMoreInteractions(mockCardSetRepository);
  });
}