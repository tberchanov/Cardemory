import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';

class PageCardsListFactory extends AppPageFactory {
  @override
  AppPage? build(String route) {
    if (route == "cards") {
      return PageCardsList();
    } else {
      return null;
    }
  }
}

class PageCardsList extends AppPage {
  @override
  String get routeName => "/cards";

  @override
  Widget buildChild() {
    return Scaffold(
      appBar: AppBar(title: Text("Cards")),
    );
  }
}
