// GENERATED CODE - DO NOT MODIFY BY HAND

// **************************************************************************
// InjectableConfigGenerator
// **************************************************************************

import 'package:get_it/get_it.dart' as _i1;
import 'package:injectable/injectable.dart' as _i2;
import 'package:sqflite/sqflite.dart' as _i28;
import 'package:sqflite/sqlite_api.dart' as _i26;

import '../core/navigation/app_page.dart' as _i3;
import '../core/navigation/app_page_factory.dart' as _i14;
import '../core/navigation/app_router_delegate.dart' as _i24;
import '../core/navigation/nav_bloc.dart' as _i29;
import '../core/navigation/navigation_module.dart' as _i34;
import '../core/navigation/navigation_registry.dart' as _i17;
import '../core/navigation/pages_extractor.dart' as _i18;
import '../data/card/card_repository_stub.dart' as _i5;
import '../data/card/db/card_repository_db.dart' as _i25;
import '../data/card/db/mappers/card_to_map_mapper.dart' as _i9;
import '../data/card/db/mappers/map_to_card_mapper.dart' as _i15;
import '../data/card_set/card_set_repository_stub.dart' as _i7;
import '../data/card_set/db/card_set_repository_db.dart' as _i27;
import '../data/card_set/db/mappers/card_set_to_map_mapper.dart' as _i8;
import '../data/card_set/db/mappers/map_to_card_set_mapper.dart' as _i16;
import '../domain/card/repository/card_repository.dart' as _i4;
import '../domain/card/usecase/get_cards_list_use_case.dart' as _i13;
import '../domain/card/usecase/save_card_use_case.dart' as _i20;
import '../domain/card/usecase/validate_card_use_case.dart' as _i23;
import '../domain/card_set/repository/card_set_repository.dart' as _i6;
import '../domain/card_set/usecase/get_card_set_list_use_case.dart' as _i11;
import '../domain/card_set/usecase/get_card_set_use_case.dart' as _i12;
import '../domain/card_set/usecase/save_card_set_use_case.dart' as _i19;
import '../domain/card_set/usecase/validate_card_set_use_case.dart' as _i22;
import '../domain/training/usecase/collect_training_data_use_case.dart' as _i10;
import '../presentation/card_set_list/bloc/card_set_list_bloc.dart' as _i30;
import '../presentation/cards_list/bloc/cards_list_bloc.dart' as _i31;
import '../presentation/create_card/bloc/create_card_bloc.dart' as _i32;
import '../presentation/create_card_set/bloc/create_card_set_bloc.dart' as _i33;
import '../presentation/training/bloc/training_bloc.dart' as _i21;

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
  gh.factory<_i10.CollectTrainingDataUseCase>(
      () => _i10.CollectTrainingDataUseCase());
  gh.factory<_i11.GetCardSetListUseCase>(
      () => _i11.GetCardSetListUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i12.GetCardSetUseCase>(
      () => _i12.GetCardSetUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i13.GetCardsListUseCase>(
      () => _i13.GetCardsListUseCase(get<_i4.CardRepository>()));
  gh.factory<List<_i14.AppPageFactory>>(() => navigationModule.pageFactories);
  gh.factory<_i15.MapToCardMapper>(() => _i15.MapToCardMapper());
  gh.factory<_i16.MapToCardSetMapper>(() => _i16.MapToCardSetMapper());
  gh.singleton<_i17.NavigationRegistry>(_i17.NavigationRegistry(
      initialPage: get<_i3.AppPage>(instanceName: 'InitialPage')));
  gh.factory<_i18.PagesExtractor>(() => _i18.PagesExtractor(
      get<_i3.AppPage>(instanceName: 'InitialPage'),
      get<_i3.AppPage>(instanceName: 'NotFoundPage'),
      get<List<_i14.AppPageFactory>>()));
  gh.factory<_i19.SaveCardSetUseCase>(
      () => _i19.SaveCardSetUseCase(get<_i6.CardSetRepository>()));
  gh.factory<_i20.SaveCardUseCase>(
      () => _i20.SaveCardUseCase(get<_i4.CardRepository>()));
  gh.factory<_i21.TrainingBloc>(() => _i21.TrainingBloc());
  gh.factory<_i22.ValidateCardSetUseCase>(() => _i22.ValidateCardSetUseCase());
  gh.factory<_i23.ValidateCardUseCase>(() => _i23.ValidateCardUseCase());
  gh.factory<_i24.AppRouterDelegate>(() => _i24.AppRouterDelegate(
      get<_i17.NavigationRegistry>(), get<_i18.PagesExtractor>()));
  gh.lazySingleton<_i4.CardRepository>(
      () => _i25.CardRepositoryDb(get<_i26.Database>(),
          get<_i9.CardToMapMapper>(), get<_i15.MapToCardMapper>()),
      registerFor: {_mobile});
  gh.lazySingleton<_i6.CardSetRepository>(
      () => _i27.CardSetRepositoryDb(get<_i28.Database>(),
          get<_i8.CardSetToMapMapper>(), get<_i16.MapToCardSetMapper>()),
      registerFor: {_mobile});
  gh.singleton<_i29.NavBloc>(_i29.NavBloc(get<_i17.NavigationRegistry>()));
  gh.factory<_i30.CardSetListBloc>(() => _i30.CardSetListBloc(
      get<_i11.GetCardSetListUseCase>(), get<_i29.NavBloc>()));
  gh.factory<_i31.CardsListBloc>(() => _i31.CardsListBloc(
      get<_i12.GetCardSetUseCase>(),
      get<_i13.GetCardsListUseCase>(),
      get<_i10.CollectTrainingDataUseCase>(),
      get<_i29.NavBloc>()));
  gh.factory<_i32.CreateCardBloc>(() => _i32.CreateCardBloc(get<_i29.NavBloc>(),
      get<_i23.ValidateCardUseCase>(), get<_i20.SaveCardUseCase>()));
  gh.factory<_i33.CreateCardSetBloc>(() => _i33.CreateCardSetBloc(
      get<_i19.SaveCardSetUseCase>(),
      get<_i29.NavBloc>(),
      get<_i22.ValidateCardSetUseCase>()));
  return get;
}

class _$NavigationModule extends _i34.NavigationModule {}
