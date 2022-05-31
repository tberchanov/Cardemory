import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:cardemory/core/navigation/pages_extractor.dart';
import 'package:cardemory/features/card_set_list/data/repositories/card_set_repository_stub.dart';
import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:cardemory/features/card_set_list/presentation/page_card_set_list.dart';
import 'package:cardemory/features/cards_list/presentation/page_cards_list.dart';
import 'package:cardemory/features/create_card_set/domain/usecases/save_card_set.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_bloc.dart';
import 'package:cardemory/features/create_card_set/presentation/page_create_card_set.dart';
import 'package:cardemory/features/not_found/page_not_found.dart';
import 'package:get_it/get_it.dart';

import 'features/card_set_list/domain/repositories/card_set_repository.dart';
import 'features/card_set_list/data/repositories/card_set_repository_db.dart';
import 'features/card_set_list/domain/usecases/get_card_set_list.dart';
import 'package:flutter/foundation.dart' show kIsWeb;

GetIt getIt = GetIt.instance;

void init() {
  getIt.registerSingleton(NavigationRegistry(initialPage: PageCardSetList()));
  getIt.registerSingleton(NavBloc(getIt.get()));
  getIt.registerFactory(() => PagesExtractor(
        PageCardSetList(),
        PageNotFound(),
        [
          PageCreateCardSetFactory(),
          PageCardsListFactory()
        ],
      ));

  getIt.registerFactory(() => CardSetListBloc(getIt.get(), getIt.get()));

  getIt.registerFactory(() => GetCardSetList(getIt.get()));

  getIt.registerSingleton(DB());
  getIt.registerLazySingleton<CardSetRepository>(() {
    if (kIsWeb) {
      // Web does not support DB. Should be used API repository later.
      return CardSetRepositoryStub();
    } else {
      return CardSetRepositoryDb.fromDB(getIt.get());
    }
  });

  getIt.registerFactory(() => SaveCardSet(getIt.get()));
  getIt.registerFactory(() => CreateCardSetBloc(getIt.get(), getIt.get()));

  getIt.allReadySync();
}
