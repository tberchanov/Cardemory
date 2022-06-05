import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/navigation/route_path.dart';

class PagesExtractor {
  final List<AppPageFactory> _factoryList;
  final AppPage _initialPage;
  final AppPage _notFoundPage;

  const PagesExtractor(
    this._initialPage,
    this._notFoundPage,
    this._factoryList,
  );

  List<AppPage> extract(RoutePath routePath) {
    List<AppPage> pages = [_initialPage];

    final path = routePath.path;
    if (path != null) {
      final uri = Uri.tryParse(path);
      if (uri != null) {
        final segments = uri.pathSegments;
        for (int index = 0; index < segments.length; index++) {
          final segment = segments[index];
          final page = _build(RouteData(segment, index, uri));
          if (page == null) {
            pages.add(_notFoundPage);
            break;
          } else {
            pages.add(page);
          }
        }
      }
    }

    return pages;
  }

  AppPage? _build(RouteData routeData) {
    for (final factory in _factoryList) {
      final page = factory.build(routeData);
      if (page != null) {
        return page;
      }
    }
    return null;
  }
}
