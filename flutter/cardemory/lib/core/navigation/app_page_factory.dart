import 'package:cardemory/core/navigation/app_page.dart';

class RouteData {
  final String route;
  final int routeIndex;
  final Uri uri;

  RouteData(this.route, this.routeIndex, this.uri);

  @override
  String toString() {
    return 'RouteData{route: $route, routeIndex: $routeIndex, uri: $uri}';
  }
}

abstract class AppPageFactory {
  AppPage? build(RouteData routeData);
}
