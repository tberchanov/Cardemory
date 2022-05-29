import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'card_set_list_bloc_test.mocks.dart';

@GenerateMocks([GetCardSetList, NavBloc])
void main() {
  late MockGetCardSetList mockGetCardSetList;
  late MockNavBloc mockNavBloc;
  late CardSetListBloc bloc;

  setUp(() {
    mockGetCardSetList = MockGetCardSetList();
    mockNavBloc = MockNavBloc();
    when(mockNavBloc.stream).thenAnswer((_) => const Stream.empty());
    bloc = CardSetListBloc(mockGetCardSetList, mockNavBloc);
  });

  test('CardSetListBloc initial state', () {
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => Right(List.empty()));
    expect(bloc.state, equals(CardSetListInitial()));
  });

  test('CardSetListBloc empty state', () async {
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => Right(List.empty()));
    final expectedEmits = [
      CardSetListLoading(),
      CardSetListEmpty(),
    ];

    expectLater(bloc.stream, emitsInOrder(expectedEmits));

    bloc.add(CardSetListLoad());
  });

  test('CardSetListBloc loaded state', () async {
    const expectedCardSets = [CardSet(id: 1, name: "CardSet")];
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => const Right(expectedCardSets));
    final expectedEmits = [
      CardSetListLoading(),
      const CardSetList(expectedCardSets),
    ];

    expectLater(bloc.stream, emitsInOrder(expectedEmits));

    bloc.add(CardSetListLoad());
  });

  test('CardSetListBloc load error', () async {
    const expectedFailure = FailureWithMessage("Stub Failure");
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => const Left(expectedFailure));
    final expectedEmits = [
      CardSetListLoading(),
      CardSetListError(),
    ];

    expectLater(bloc.stream, emitsInOrder(expectedEmits));

    bloc.add(CardSetListLoad());
  });
}
