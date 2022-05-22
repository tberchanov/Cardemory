import 'package:cardemory/core/navigation/route_path.dart';
import 'dart:developer' as developer;
import 'package:flutter/material.dart';

class AppRouteInformationParser extends RouteInformationParser<RoutePath> {
  @override
  Future<RoutePath> parseRouteInformation(
      RouteInformation routeInformation) async {
    developer.log("parseRouteInformation", name: "AppRouteInformationParser");
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
    developer.log("restoreRouteInformation", name: "AppRouteInformationParser");
    return RouteInformation(location: configuration.path);
  }
}
