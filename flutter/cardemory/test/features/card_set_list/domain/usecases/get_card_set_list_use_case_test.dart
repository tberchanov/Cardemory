import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set_list_use_case.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'get_card_set_list_use_case_test.mocks.dart';

@GenerateMocks([CardSetRepository])
void main() {
  late GetCardSetListUseCase usecase;
  late MockCardSetRepository mockCardSetRepository;

  setUp(() {
    mockCardSetRepository = MockCardSetRepository();
    usecase = GetCardSetListUseCase(mockCardSetRepository);
  });

  final List<CardSet> cardSets = [];

  test('should get card set list from repository', () async {
    when(mockCardSetRepository.getCardSets())
        .thenAnswer((_) async => Right(cardSets));

    final result = await usecase(NoParams());

    expect(result, Right(cardSets));
    verify(mockCardSetRepository.getCardSets());
    verifyNoMoreInteractions(mockCardSetRepository);
  });
}
