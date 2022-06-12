import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/presentation/card_set_list/page_card_set_list.dart';
import 'package:cardemory/presentation/cards_list/page_cards_list.dart';
import 'package:cardemory/presentation/create_card/page_create_card.dart';
import 'package:cardemory/presentation/create_card_set/page_create_card_set.dart';
import 'package:cardemory/presentation/not_found/page_not_found.dart';
import 'package:cardemory/presentation/training/page_training.dart';
import 'package:injectable/injectable.dart';

const namedInitialPage = Named("InitialPage");
const namedNotFoundPage = Named("NotFoundPage");

@module
abstract class NavigationModule {
  @namedInitialPage
  AppPage get initialPage => PageCardSetList();

  @namedNotFoundPage
  AppPage get notFoundPage => PageNotFound();

  @injectable
  List<AppPageFactory> get pageFactories => [
        PageCreateCardSetFactory(),
        PageCardsListFactory(),
        PageCreateCardFactory(),
        PageTrainingFactory(),
      ];
}
