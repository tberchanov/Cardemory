// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart' as _i2;
import 'package:sqflite/sqflite.dart' as _i29;
import 'package:sqflite/sqlite_api.dart' as _i27;

import '../core/navigation/app_page.dart' as _i3;
import '../core/navigation/app_page_factory.dart' as _i15;
import '../core/navigation/app_router_delegate.dart' as _i25;
import '../core/navigation/nav_bloc.dart' as _i31;
import '../core/navigation/navigation_module.dart' as _i36;
import '../core/navigation/navigation_registry.dart' as _i18;
import '../core/navigation/pages_extractor.dart' as _i19;
import '../data/card/card_repository_stub.dart' as _i5;
import '../data/card/db/card_repository_db.dart' as _i26;
import '../data/card/db/mappers/card_to_map_mapper.dart' as _i9;
import '../data/card/db/mappers/map_to_card_mapper.dart' as _i16;
import '../data/card_set/card_set_repository_stub.dart' as _i7;
import '../data/card_set/db/card_set_repository_db.dart' as _i28;
import '../data/card_set/db/mappers/card_set_to_map_mapper.dart' as _i8;
import '../data/card_set/db/mappers/map_to_card_set_mapper.dart' as _i17;
import '../domain/card/repository/card_repository.dart' as _i4;
import '../domain/card/usecase/get_cards_list_use_case.dart' as _i12;
import '../domain/card/usecase/save_card_use_case.dart' as _i21;
import '../domain/card/usecase/validate_card_use_case.dart' as _i24;
import '../domain/card_set/repository/card_set_repository.dart' as _i6;
import '../domain/card_set/usecase/get_card_set_list_use_case.dart' as _i10;
import '../domain/card_set/usecase/get_card_set_use_case.dart' as _i11;
import '../domain/card_set/usecase/save_card_set_use_case.dart' as _i20;
import '../domain/card_set/usecase/validate_card_set_use_case.dart' as _i23;
import '../domain/training/usecase/collect_training_data_use_case.dart' as _i30;
import '../domain/training/usecase/get_min_cards_for_training_use_case.dart'
    as _i13;
import '../domain/training/usecase/is_training_available_use_case.dart' as _i14;
import '../presentation/card_set_list/bloc/card_set_list_bloc.dart' as _i32;
import '../presentation/cards_list/bloc/cards_list_bloc.dart' as _i33;
import '../presentation/create_card/bloc/create_card_bloc.dart' as _i34;
import '../presentation/create_card_set/bloc/create_card_set_bloc.dart' as _i35;
import '../presentation/training/bloc/training_bloc.dart' as _i22;

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
  gh.factory<_i12.GetCardsListUseCase>(
      () => _i12.GetCardsListUseCase(get<_i4.CardRepository>()));
  gh.factory<_i13.GetMinCardsForTrainingUseCase>(
      () => _i13.GetMinCardsForTrainingUseCase());
  gh.factory<_i14.IsTrainingAvailableUseCase>(() =>
      _i14.IsTrainingAvailableUseCase(
          get<_i13.GetMinCardsForTrainingUseCase>()));
  gh.factory<List<_i15.AppPageFactory>>(() => navigationModule.pageFactories);
  gh.factory<_i16.MapToCardMapper>(() => _i16.MapToCardMapper());
  gh.factory<_i17.MapToCardSetMapper>(() => _i17.MapToCardSetMapper());
  gh.singleton<_i18.NavigationRegistry>(_i18.NavigationRegistry(
      initialPage: get<_i3.AppPage>(instanceName: 'InitialPage')));
  gh.factory<_i19.PagesExtractor>(() => _i19.PagesExtractor(
      get<_i3.AppPage>(instanceName: 'InitialPage'),
      get<_i3.AppPage>(instanceName: 'NotFoundPage'),
      get<List<_i15.AppPageFactory>>()));
  gh.factory<_i20.SaveCardSetUseCase>(
      () => _i20.SaveCardSetUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i21.SaveCardUseCase>(
      () => _i21.SaveCardUseCase(get<_i4.CardRepository>()));
  gh.factory<_i22.TrainingBloc>(() => _i22.TrainingBloc());
  gh.factory<_i23.ValidateCardSetUseCase>(() => _i23.ValidateCardSetUseCase());
  gh.factory<_i24.ValidateCardUseCase>(() => _i24.ValidateCardUseCase());
  gh.factory<_i25.AppRouterDelegate>(() => _i25.AppRouterDelegate(
      get<_i18.NavigationRegistry>(), get<_i19.PagesExtractor>()));
  gh.lazySingleton<_i4.CardRepository>(
      () => _i26.CardRepositoryDb(get<_i27.Database>(),
          get<_i9.CardToMapMapper>(), get<_i16.MapToCardMapper>()),
      registerFor: {_mobile});
  gh.lazySingleton<_i6.CardSetRepository>(
      () => _i28.CardSetRepositoryDb(get<_i29.Database>(),
          get<_i8.CardSetToMapMapper>(), get<_i17.MapToCardSetMapper>()),
      registerFor: {_mobile});
  gh.factory<_i30.CollectTrainingDataUseCase>(() =>
      _i30.CollectTrainingDataUseCase(get<_i4.CardRepository>(),
          get<_i13.GetMinCardsForTrainingUseCase>()));
  gh.singleton<_i31.NavBloc>(_i31.NavBloc(get<_i18.NavigationRegistry>()));
  gh.factory<_i32.CardSetListBloc>(() => _i32.CardSetListBloc(
      get<_i10.GetCardSetListUseCase>(), get<_i31.NavBloc>()));
  gh.factory<_i33.CardsListBloc>(() => _i33.CardsListBloc(
      get<_i11.GetCardSetUseCase>(),
      get<_i12.GetCardsListUseCase>(),
      get<_i30.CollectTrainingDataUseCase>(),
      get<_i14.IsTrainingAvailableUseCase>(),
      get<_i31.NavBloc>()));
  gh.factory<_i34.CreateCardBloc>(() => _i34.CreateCardBloc(get<_i31.NavBloc>(),
      get<_i24.ValidateCardUseCase>(), get<_i21.SaveCardUseCase>()));
  gh.factory<_i35.CreateCardSetBloc>(() => _i35.CreateCardSetBloc(
      get<_i20.SaveCardSetUseCase>(),
      get<_i31.NavBloc>(),
      get<_i23.ValidateCardSetUseCase>()));
  return get;
}

class _$NavigationModule extends _i36.NavigationModule {}
