import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:flutter/material.dart';

class PageTrainingFactory extends AppPageFactory {
  @override
  AppPage? build(RouteData routeData) {
    if (routeData.route == "training") {
      return PageTraining();
    }
    return null;
  }
}

class PageTraining extends AppPage {
  @override
  String get routeName => "/training";

  @override
  Widget buildChild() {
    return const Scaffold(
      body: Center(
        child: Text("Training"),
      ),
    );
  }
}
