import 'package:cardemory/core/navigation/app_page.dart';
import 'package:flutter/material.dart';

class PageNotFound extends AppPage {
  PageNotFound()
      : super(Scaffold(
          appBar: AppBar(),
          body: Center(
            child: Text("Page Not Found"),
          ),
        ));

  @override
  String get routeName => "/404";
}
