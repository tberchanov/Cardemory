import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/save_card_set.dart';
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

  final CardSet cardSetParam = CardSet(1, "Param Stub", List.empty());
  final CardSet cardSetResult = CardSet(-1, "Result Stub", List.empty());

  test('should save card set to repository', () async {
    when(mockCardSetRepository.saveCardSet(cardSetParam))
        .thenAnswer((realInvocation) async => Right(cardSetResult));

    final result = await usecase(cardSetParam);

    expect(result, Right(cardSetResult));
    verify(mockCardSetRepository.saveCardSet(cardSetParam));
    verifyNoMoreInteractions(mockCardSetRepository);
  });
}