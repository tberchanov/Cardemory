import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:flutter/material.dart';

class PageCreateCardSetFactory extends AppPageFactory {
  @override
  AppPage? build(String route) {
    if (route == "card-set-create") {
      return PageCreateCardSet();
    } else {
      return null;
    }
  }
}

class PageCreateCardSet extends AppPage {
  PageCreateCardSet()
      : super(
          const Scaffold(
            // appBar: AppBar(),
            body: Center(
              child: Text("Create Card Set"),
            ),
          ),
        );

  @override
  String get routeName => "/card-set-create";
}
