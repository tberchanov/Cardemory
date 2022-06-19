// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart' as _i2;
import 'package:sqflite/sqflite.dart' as _i32;
import 'package:sqflite/sqlite_api.dart' as _i30;

import '../core/navigation/app_page.dart' as _i3;
import '../core/navigation/app_page_factory.dart' as _i15;
import '../core/navigation/app_router_delegate.dart' as _i27;
import '../core/navigation/nav_bloc.dart' as _i34;
import '../core/navigation/navigation_module.dart' as _i40;
import '../core/navigation/navigation_registry.dart' as _i18;
import '../core/navigation/pages_extractor.dart' as _i19;
import '../data/card/card_repository_stub.dart' as _i5;
import '../data/card/db/card_repository_db.dart' as _i29;
import '../data/card/db/mappers/card_to_map_mapper.dart' as _i9;
import '../data/card/db/mappers/map_to_card_mapper.dart' as _i16;
import '../data/card_set/card_set_repository_stub.dart' as _i7;
import '../data/card_set/db/card_set_repository_db.dart' as _i31;
import '../data/card_set/db/mappers/card_set_to_map_mapper.dart' as _i8;
import '../data/card_set/db/mappers/map_to_card_set_mapper.dart' as _i17;
import '../domain/card/repository/card_repository.dart' as _i4;
import '../domain/card/usecase/get_cards_list_use_case.dart' as _i13;
import '../domain/card/usecase/save_card_use_case.dart' as _i24;
import '../domain/card/usecase/validate_card_use_case.dart' as _i26;
import '../domain/card_set/repository/card_set_repository.dart' as _i6;
import '../domain/card_set/usecase/get_card_set_list_use_case.dart' as _i10;
import '../domain/card_set/usecase/get_card_set_use_case.dart' as _i11;
import '../domain/card_set/usecase/save_card_set_use_case.dart' as _i23;
import '../domain/card_set/usecase/validate_card_set_use_case.dart' as _i25;
import '../domain/memory/memory_manager.dart' as _i21;
import '../domain/training/usecase/calculate_training_result_use_case.dart'
    as _i28;
import '../domain/training/usecase/collect_training_data_use_case.dart' as _i33;
import '../domain/training/usecase/get_cards_for_training_amount_use_case.dart'
    as _i12;
import '../domain/training/usecase/is_training_available_use_case.dart' as _i14;
import '../domain/training/usecase/process_forgotten_card_use_case.dart'
    as _i20;
import '../domain/training/usecase/process_remembered_card_use_case.dart'
    as _i22;
import '../presentation/card_set_list/bloc/card_set_list_bloc.dart' as _i36;
import '../presentation/cards_list/bloc/cards_list_bloc.dart' as _i37;
import '../presentation/create_card/bloc/create_card_bloc.dart' as _i38;
import '../presentation/create_card_set/bloc/create_card_set_bloc.dart' as _i39;
import '../presentation/training/bloc/training_bloc.dart' as _i35;

const String _web = 'web';
const String _mobile = 'mobile';
// ignore_for_file: unnecessary_lambdas
// ignore_for_file: lines_longer_than_80_chars
/// initializes the registration of provided dependencies inside of [GetIt]
_i1.GetIt $initGetIt(_i1.GetIt get,
    {String? environment, _i2.EnvironmentFilter? environmentFilter}) {
  final gh = _i2.GetItHelper(get, environment, environmentFilter);
  final navigationModule = _$NavigationModule();
  gh.factory<_i3.AppPage>(() => navigationModule.initialPage,
      instanceName: 'InitialPage');
  gh.factory<_i3.AppPage>(() => navigationModule.notFoundPage,
      instanceName: 'NotFoundPage');
  gh.lazySingleton<_i4.CardRepository>(() => _i5.CardRepositoryStub(),
      registerFor: {_web});
  gh.lazySingleton<_i6.CardSetRepository>(() => _i7.CardSetRepositoryStub(),
      registerFor: {_web});
  gh.factory<_i8.CardSetToMapMapper>(() => _i8.CardSetToMapMapper());
  gh.factory<_i9.CardToMapMapper>(() => _i9.CardToMapMapper());
  gh.factory<_i10.GetCardSetListUseCase>(
      () => _i10.GetCardSetListUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i11.GetCardSetUseCase>(
      () => _i11.GetCardSetUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i12.GetCardsForTrainingAmountUseCase>(
      () => _i12.GetCardsForTrainingAmountUseCase());
  gh.factory<_i13.GetCardsListUseCase>(
      () => _i13.GetCardsListUseCase(get<_i4.CardRepository>()));
  gh.factory<_i14.IsTrainingAvailableUseCase>(() =>
      _i14.IsTrainingAvailableUseCase(
          get<_i12.GetCardsForTrainingAmountUseCase>()));
  gh.factory<List<_i15.AppPageFactory>>(() => navigationModule.pageFactories);
  gh.factory<_i16.MapToCardMapper>(() => _i16.MapToCardMapper());
  gh.factory<_i17.MapToCardSetMapper>(() => _i17.MapToCardSetMapper());
  gh.singleton<_i18.NavigationRegistry>(_i18.NavigationRegistry(
      initialPage: get<_i3.AppPage>(instanceName: 'InitialPage')));
  gh.factory<_i19.PagesExtractor>(() => _i19.PagesExtractor(
      get<_i3.AppPage>(instanceName: 'InitialPage'),
      get<_i3.AppPage>(instanceName: 'NotFoundPage'),
      get<List<_i15.AppPageFactory>>()));
  gh.factory<_i20.ProcessForgottenCardUseCase>(() =>
      _i20.ProcessForgottenCardUseCase(
          get<_i4.CardRepository>(), get<_i21.MemoryManager>()));
  gh.factory<_i22.ProcessRememberedCardUseCase>(() =>
      _i22.ProcessRememberedCardUseCase(
          get<_i4.CardRepository>(), get<_i21.MemoryManager>()));
  gh.factory<_i23.SaveCardSetUseCase>(
      () => _i23.SaveCardSetUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i24.SaveCardUseCase>(
      () => _i24.SaveCardUseCase(get<_i4.CardRepository>()));
  gh.factory<_i25.ValidateCardSetUseCase>(() => _i25.ValidateCardSetUseCase());
  gh.factory<_i26.ValidateCardUseCase>(() => _i26.ValidateCardUseCase());
  gh.lazySingleton<_i27.AppRouterDelegate>(() => _i27.AppRouterDelegate(
      get<_i18.NavigationRegistry>(), get<_i19.PagesExtractor>()));
  gh.factory<_i28.CalculateTrainingResultUseCase>(() =>
      _i28.CalculateTrainingResultUseCase(
          get<_i12.GetCardsForTrainingAmountUseCase>()));
  gh.lazySingleton<_i4.CardRepository>(
      () => _i29.CardRepositoryDb(get<_i30.Database>(),
          get<_i9.CardToMapMapper>(), get<_i16.MapToCardMapper>()),
      registerFor: {_mobile});
  gh.lazySingleton<_i6.CardSetRepository>(
      () => _i31.CardSetRepositoryDb(get<_i32.Database>(),
          get<_i8.CardSetToMapMapper>(), get<_i17.MapToCardSetMapper>()),
      registerFor: {_mobile});
  gh.factory<_i33.CollectTrainingDataUseCase>(() =>
      _i33.CollectTrainingDataUseCase(get<_i4.CardRepository>(),
          get<_i12.GetCardsForTrainingAmountUseCase>()));
  gh.lazySingleton<_i34.NavBloc>(() => _i34.NavBloc(
      get<_i18.NavigationRegistry>(), get<_i27.AppRouterDelegate>()));
  gh.factory<_i35.TrainingBloc>(() => _i35.TrainingBloc(
      get<_i22.ProcessRememberedCardUseCase>(),
      get<_i20.ProcessForgottenCardUseCase>(),
      get<_i28.CalculateTrainingResultUseCase>()));
  gh.factory<_i36.CardSetListBloc>(() => _i36.CardSetListBloc(
      get<_i10.GetCardSetListUseCase>(), get<_i34.NavBloc>()));
  gh.factory<_i37.CardsListBloc>(() => _i37.CardsListBloc(
      get<_i11.GetCardSetUseCase>(),
      get<_i13.GetCardsListUseCase>(),
      get<_i33.CollectTrainingDataUseCase>(),
      get<_i14.IsTrainingAvailableUseCase>(),
      get<_i34.NavBloc>()));
  gh.factory<_i38.CreateCardBloc>(() => _i38.CreateCardBloc(get<_i34.NavBloc>(),
      get<_i26.ValidateCardUseCase>(), get<_i24.SaveCardUseCase>()));
  gh.factory<_i39.CreateCardSetBloc>(() => _i39.CreateCardSetBloc(
      get<_i23.SaveCardSetUseCase>(),
      get<_i34.NavBloc>(),
      get<_i25.ValidateCardSetUseCase>()));
  return get;
}

class _$NavigationModule extends _i40.NavigationModule {}
