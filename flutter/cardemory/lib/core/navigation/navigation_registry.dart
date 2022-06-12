import 'package:cardemory/core/navigation/app_page.dart';
import 'package:injectable/injectable.dart';

import 'navigation_module.dart';

@singleton
class NavigationRegistry {
  final List<AppPage> _pages = [];
  final AppPage initialPage;

  NavigationRegistry({@namedInitialPage required this.initialPage}) {
    _pages.add(initialPage);
  }

  void addPage(AppPage page) {
    _pages.add(page);
  }

  void addPages(List<AppPage> pages) {
    _pages.addAll(pages);
  }

  void popPage() {
    _pages.removeLast();
  }

  List<AppPage> getPages() {
    return List.of(_pages);
  }

  void clear() {
    _pages.clear();
  }
}
