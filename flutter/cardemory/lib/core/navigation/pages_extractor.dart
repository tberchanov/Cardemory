import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/navigation/navigation_module.dart';
import 'package:cardemory/core/navigation/route_path.dart';
import 'package:injectable/injectable.dart';

@injectable
class PagesExtractor {
  final List<AppPageFactory> _factoryList;
  final AppPage _initialPage;
  final AppPage _notFoundPage;

  const PagesExtractor(
    @namedInitialPage this._initialPage,
    @namedNotFoundPage this._notFoundPage,
    this._factoryList,
  );

  Future<List<AppPage>> extract(RoutePath routePath) async {
    List<AppPage> pages = [_initialPage];

    final path = routePath.path;
    if (path != null) {
      final uri = Uri.tryParse(path);
      if (uri != null) {
        final segments = uri.pathSegments;
        for (int index = 0; index < segments.length; index++) {
          final segment = segments[index];
          final page = await _build(RouteData(segment, index, uri));
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

  Future<AppPage?> _build(RouteData routeData) async {
    for (final factory in _factoryList) {
      final page = await factory.build(routeData);
      if (page != null) {
        return page;
      }
    }
    return null;
  }
}
