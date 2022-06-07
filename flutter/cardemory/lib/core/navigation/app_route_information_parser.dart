import 'package:cardemory/core/navigation/route_path.dart';
import 'package:flutter/material.dart';
import 'package:logging/logging.dart';

class AppRouteInformationParser extends RouteInformationParser<RoutePath> {
  final _log = Logger('AppRouteInformationParser');

  @override
  Future<RoutePath> parseRouteInformation(RouteInformation routeInformation) async {
    _log.info("parseRouteInformation: ${routeInformation.location}");
    final uri = Uri.tryParse(routeInformation.location ?? "");
    if (uri == null) {
      return RoutePath(routeInformation.location);
    } else {
      final pathSegments = List.of(uri.pathSegments);
      _removeEqualItems(pathSegments);
      return RoutePath("/" + pathSegments.join("/"));
    }
  }

  void _removeEqualItems(List<String> segments) {
    for (int i = 0; i < segments.length - 1; i++) {
      for (int j = i+1; j < segments.length; j++) {
        if (segments[i] == segments[j]) {
          segments.removeAt(j);
        }
      }
    }
  }

  @override
  RouteInformation restoreRouteInformation(RoutePath configuration) {
    _log.info("restoreRouteInformation: ${configuration.path}");
    return RouteInformation(location: configuration.path);
  }
}
