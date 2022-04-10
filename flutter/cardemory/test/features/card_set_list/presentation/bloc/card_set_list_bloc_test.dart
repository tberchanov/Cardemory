import 'package:cardemory/core/error/failures.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:cardemory/features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:dartz/dartz.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import 'card_set_list_bloc_test.mocks.dart';

@GenerateMocks([GetCardSetList])
void main() {
  late MockGetCardSetList mockGetCardSetList;
  late CardSetListBloc bloc;

  setUp(() {
    mockGetCardSetList = MockGetCardSetList();
    bloc = CardSetListBloc(mockGetCardSetList);
  });

  test('CardSetListBloc initial state', () {
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
    final expectedCardSets = [CardSet(id: 1, name: "CardSet")];
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => Right(expectedCardSets));
    final expectedEmits = [
      CardSetListLoading(),
      CardSetList(expectedCardSets),
    ];

    expectLater(bloc.stream, emitsInOrder(expectedEmits));

    bloc.add(CardSetListLoad());
  });

  test('CardSetListBloc load error', () async {
    final expectedFailure = FailureWithMessage("Stub Failure");
    when(mockGetCardSetList.call(any))
        .thenAnswer((_) async => Left(expectedFailure));
    final expectedEmits = [
      CardSetListLoading(),
      CardSetListError(),
    ];

    expectLater(bloc.stream, emitsInOrder(expectedEmits));

    bloc.add(CardSetListLoad());
  });
}
