import 'package:cardemory/core/navigation/app_page.dart';
import 'package:flutter/material.dart';

class PageNotFound extends AppPage {
  @override
  Widget buildChild() {
    return const Scaffold(
      body: Center(
        child: Text("Page Not Found"),
      ),
    );
  }

  @override
  String get routeName => "/404";
}
