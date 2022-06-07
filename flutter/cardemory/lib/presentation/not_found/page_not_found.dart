import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/presentation/not_found/widget/page_not_found_content.dart';
import 'package:flutter/material.dart';

class PageNotFound extends AppPage {
  @override
  Widget buildChild() {
    return const PageNotFoundContent();
  }

  @override
  String get routeName => "/404";
}
