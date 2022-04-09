import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/GetCardSetList.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';
import 'get_card_set_list_test.mocks.dart';

@GenerateMocks([CardSetRepository])
void main() {
  late GetCardSetList usecase;
  late MockCardSetRepository mockCardSetRepository;

  setUp(() {
    mockCardSetRepository = MockCardSetRepository();
    usecase = GetCardSetList(mockCardSetRepository);
  });

  final List<CardSet> cardSets = [];

  test('should get card set list from repository', () async {
    when(mockCardSetRepository.getCardSets())
        .thenAnswer((_) async => Right(cardSets));

    final result = await usecase.execute();

    expect(result, Right(cardSets));
    verify(mockCardSetRepository.getCardSets());
    verifyNoMoreInteractions(mockCardSetRepository);
  });
}
