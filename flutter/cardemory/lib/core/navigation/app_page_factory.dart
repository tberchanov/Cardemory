import 'package:cardemory/core/navigation/app_page.dart';

abstract class AppPageFactory {
  AppPage? build(String route);
}