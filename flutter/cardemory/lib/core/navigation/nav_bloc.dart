import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_router_delegate.dart';
import 'package:cardemory/core/navigation/nav_event.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:injectable/injectable.dart';
import 'package:logging/logging.dart';

@lazySingleton
class NavBloc extends Bloc<NavEvent, List<AppPage>> {
  static final _log = Logger('NavBloc');
  final NavigationRegistry navRegistry;
  final AppRouterDelegate _appRouterDelegate;

  NavBloc(this.navRegistry, this._appRouterDelegate) : super(navRegistry.getPages()) {
    on<NavEvent>((event, emit) async {
      _log.info("On event: $event");

      if (event is AddPage) {
        navRegistry.addPage(event.page);
        emit.call(navRegistry.getPages());
      } else if (event is PopPage) {
        navRegistry.popPage();
        emit.call(navRegistry.getPages());
      } else if (event is ResetToInitial) {
        navRegistry.clear();
        navRegistry.addPage(navRegistry.initialPage);
        emit.call(navRegistry.getPages());
      } else if (event is RegisterOnPopPage) {
        _appRouterDelegate.registerOnPopPageListener(event.onPopPage);
      } else if (event is UnregisterOnPopPage) {
        _appRouterDelegate.unregisterOnPopPageListener();
      }
    });
  }
}
