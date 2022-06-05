import 'package:cardemory/core/db.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:cardemory/core/navigation/pages_extractor.dart';
import 'package:cardemory/data/card_set/card_set_repository_stub.dart';
import 'package:cardemory/data/card_set/db/card_set_repository_db.dart';
import 'package:cardemory/domain/card/usecase/validate_card.dart';
import 'package:cardemory/domain/card_set/repository/card_set_repository.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set.dart';
import 'package:cardemory/domain/card_set/usecase/save_card_set.dart';
import 'package:cardemory/presentation/card_set_list/bloc/card_set_list_bloc.dart';
import 'package:cardemory/presentation/card_set_list/page_card_set_list.dart';
import 'package:cardemory/presentation/cards_list/bloc/cards_list_bloc.dart';
import 'package:cardemory/presentation/cards_list/page_cards_list.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_bloc.dart';
import 'package:cardemory/presentation/create_card/page_create_card.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_bloc.dart';
import 'package:cardemory/presentation/create_card_set/page_create_card_set.dart';
import 'package:cardemory/presentation/not_found/page_not_found.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:get_it/get_it.dart';
import 'domain/card_set/usecase/get_card_set_list.dart';

GetIt getIt = GetIt.instance;

void init() {
  getIt.registerSingleton(NavigationRegistry(initialPage: PageCardSetList()));
  getIt.registerSingleton(NavBloc(getIt.get()));
  getIt.registerFactory(() => PagesExtractor(
        PageCardSetList(),
        PageNotFound(),
        [
          PageCreateCardSetFactory(),
          PageCardsListFactory(),
          PageCreateCardFactory(),
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

  getIt.registerFactory(() => GetCardSet(getIt.get()));
  getIt.registerFactory(() => CardsListBloc(getIt.get(), getIt.get()));

  getIt.registerFactory(() => ValidateCard());
  getIt.registerFactory(() => CreateCardBloc(getIt.get(), getIt.get()));

  getIt.allReadySync();
}
