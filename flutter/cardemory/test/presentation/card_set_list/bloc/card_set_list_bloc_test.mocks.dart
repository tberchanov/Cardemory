// Mocks generated by Mockito 5.1.0 from annotations
// in cardemory/test/presentation/card_set_list/bloc/card_set_list_bloc_test.dart.
// Do not manually edit this file.

import 'dart:async' as _i5;

import 'package:cardemory/core/error/failures.dart' as _i6;
import 'package:cardemory/core/navigation/app_page.dart' as _i10;
import 'package:cardemory/core/navigation/nav_bloc.dart' as _i9;
import 'package:cardemory/core/navigation/nav_event.dart' as _i11;
import 'package:cardemory/core/navigation/navigation_registry.dart' as _i3;
import 'package:cardemory/core/usecases/usecase.dart' as _i8;
import 'package:cardemory/domain/card_set/entity/card_set.dart' as _i7;
import 'package:cardemory/domain/card_set/usecase/get_card_set_list_use_case.dart'
    as _i4;
import 'package:dartz/dartz.dart' as _i2;
import 'package:flutter_bloc/flutter_bloc.dart' as _i12;
import 'package:mockito/mockito.dart' as _i1;

// ignore_for_file: type=lint
// ignore_for_file: avoid_redundant_argument_values
// ignore_for_file: avoid_setters_without_getters
// ignore_for_file: comment_references
// ignore_for_file: implementation_imports
// ignore_for_file: invalid_use_of_visible_for_testing_member
// ignore_for_file: prefer_const_constructors
// ignore_for_file: unnecessary_parenthesis
// ignore_for_file: camel_case_types

class _FakeEither_0<L, R> extends _i1.Fake implements _i2.Either<L, R> {}

class _FakeNavigationRegistry_1 extends _i1.Fake
    implements _i3.NavigationRegistry {}

/// A class which mocks [GetCardSetListUseCase].
///
/// See the documentation for Mockito's code generation for more information.
class MockGetCardSetListUseCase extends _i1.Mock
    implements _i4.GetCardSetListUseCase {
  MockGetCardSetListUseCase() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i5.Future<_i2.Either<_i6.Failure, List<_i7.CardSet>>> call(
          _i8.NoParams? param) =>
      (super.noSuchMethod(Invocation.method(#call, [param]),
          returnValue: Future<_i2.Either<_i6.Failure, List<_i7.CardSet>>>.value(
              _FakeEither_0<_i6.Failure, List<_i7.CardSet>>())) as _i5
          .Future<_i2.Either<_i6.Failure, List<_i7.CardSet>>>);
}

/// A class which mocks [NavBloc].
///
/// See the documentation for Mockito's code generation for more information.
class MockNavBloc extends _i1.Mock implements _i9.NavBloc {
  MockNavBloc() {
    _i1.throwOnMissingStub(this);
  }

  @override
  _i3.NavigationRegistry get navRegistry =>
      (super.noSuchMethod(Invocation.getter(#navRegistry),
          returnValue: _FakeNavigationRegistry_1()) as _i3.NavigationRegistry);
  @override
  List<_i10.AppPage> get state => (super.noSuchMethod(Invocation.getter(#state),
      returnValue: <_i10.AppPage>[]) as List<_i10.AppPage>);
  @override
  _i5.Stream<List<_i10.AppPage>> get stream =>
      (super.noSuchMethod(Invocation.getter(#stream),
              returnValue: Stream<List<_i10.AppPage>>.empty())
          as _i5.Stream<List<_i10.AppPage>>);
  @override
  bool get isClosed =>
      (super.noSuchMethod(Invocation.getter(#isClosed), returnValue: false)
          as bool);
  @override
  void add(_i11.NavEvent? event) =>
      super.noSuchMethod(Invocation.method(#add, [event]),
          returnValueForMissingStub: null);
  @override
  void onEvent(_i11.NavEvent? event) =>
      super.noSuchMethod(Invocation.method(#onEvent, [event]),
          returnValueForMissingStub: null);
  @override
  void emit(List<_i10.AppPage>? state) =>
      super.noSuchMethod(Invocation.method(#emit, [state]),
          returnValueForMissingStub: null);
  @override
  void on<E extends _i11.NavEvent>(
          _i12.EventHandler<E, List<_i10.AppPage>>? handler,
          {_i12.EventTransformer<E>? transformer}) =>
      super.noSuchMethod(
          Invocation.method(#on, [handler], {#transformer: transformer}),
          returnValueForMissingStub: null);
  @override
  void onTransition(
          _i12.Transition<_i11.NavEvent, List<_i10.AppPage>>? transition) =>
      super.noSuchMethod(Invocation.method(#onTransition, [transition]),
          returnValueForMissingStub: null);
  @override
  _i5.Future<void> close() => (super.noSuchMethod(Invocation.method(#close, []),
      returnValue: Future<void>.value(),
      returnValueForMissingStub: Future<void>.value()) as _i5.Future<void>);
  @override
  void onChange(_i12.Change<List<_i10.AppPage>>? change) =>
      super.noSuchMethod(Invocation.method(#onChange, [change]),
          returnValueForMissingStub: null);
  @override
  void addError(Object? error, [StackTrace? stackTrace]) =>
      super.noSuchMethod(Invocation.method(#addError, [error, stackTrace]),
          returnValueForMissingStub: null);
  @override
  void onError(Object? error, StackTrace? stackTrace) =>
      super.noSuchMethod(Invocation.method(#onError, [error, stackTrace]),
          returnValueForMissingStub: null);
}
