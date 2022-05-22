import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:cardemory/core/navigation/pages_extractor.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository_stub.dart';
import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:cardemory/features/card_set_list/presentation/page_card_set_list.dart';
import 'package:cardemory/features/create_card_set/presentation/page_create_card_set.dart';
import 'package:cardemory/features/not_found/page_not_found.dart';
import 'package:get_it/get_it.dart';

import 'features/card_set_list/domain/repositories/card_set_repository.dart';
import 'features/card_set_list/domain/repositories/card_set_repository_db.dart';
import 'features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:flutter/foundation.dart' show kIsWeb;

GetIt getIt = GetIt.instance;

Future<void> init() async {
  getIt.registerSingleton(NavigationRegistry(initialPage: PageCardSetList()));
  getIt.registerSingleton(NavBloc(getIt.get()));
  getIt.registerFactory(() => PagesExtractor(
        PageCardSetList(),
        PageNotFound(),
        [
          PageCreateCardSetFactory(),
        ],
      ));

  getIt.registerFactoryAsync(
    () async => CardSetListBloc(await getIt.getAsync()),
  );

  getIt.registerSingletonAsync(
    () async => GetCardSetList(await getIt.getAsync()),
  );

  getIt.registerLazySingletonAsync(() => DB.create());

  getIt.registerSingletonAsync<CardSetRepository>(() async {
    if (kIsWeb) {
      // Web does not support DB. Should be used API repository later.
      return CardSetRepositoryStub();
    } else {
      return CardSetRepositoryDb(await getIt.getAsync());
    }
  });

  await getIt.allReady();
}
