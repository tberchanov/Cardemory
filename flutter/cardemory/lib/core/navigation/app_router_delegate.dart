import 'dart:developer' as developer;

import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:cardemory/core/navigation/pages_extractor.dart';
import 'package:cardemory/core/navigation/route_path.dart';
import 'package:flutter/material.dart';

class AppRouterDelegate extends RouterDelegate<RoutePath>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<RoutePath> {
  final NavigationRegistry _navRegistry;
  final PagesExtractor _pagesExtractor;

  AppRouterDelegate(this._navRegistry, this._pagesExtractor);

  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigatorKey,
      pages: _navRegistry.getPages().map((e) => e.page).toList(),
      onPopPage: (route, result) {
        developer.log("onPopPage: ${route.settings.name}",
            name: "AppRouterDelegate");

        if (!route.didPop(result)) {
          return false;
        }

        _navRegistry.popPage();
        notifyListeners();
        return true;
      },
    );
  }

  @override
  Future<void> setNewRoutePath(RoutePath configuration) async {
    _navRegistry.clear();
    _navRegistry.addPages(_pagesExtractor.extract(configuration));
  }

  @override
  RoutePath get currentConfiguration => RoutePath(_navRegistry
      .getPages()
      .map((page) => page.routeName)
      .join()
      .replaceAll("//", "/"));

  @override
  final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();
}