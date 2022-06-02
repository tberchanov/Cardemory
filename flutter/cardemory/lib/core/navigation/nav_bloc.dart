import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

abstract class NavEvent extends Equatable {
  static final pop = PopPage();

  static add(AppPage page) => AddPage(page);
}

class AddPage extends NavEvent {
  final AppPage page;

  AddPage(this.page);

  @override
  String toString() => "AddPage: $page";

  @override
  List<Object?> get props => [page];
}

class PopPage extends NavEvent {
  @override
  List<Object?> get props => [];
}

class NavBloc extends Bloc<NavEvent, List<AppPage>> {
  final _log = Logger('NavBloc');
  final NavigationRegistry navRegistry;

  NavBloc(this.navRegistry) : super(navRegistry.getPages()) {
    on<NavEvent>((event, emit) async {
      _log.info("On event: $event");

      if (event is AddPage) {
        navRegistry.addPage(event.page);
        emit.call(navRegistry.getPages());
      } else if (event is PopPage) {
        navRegistry.popPage();
        emit.call(navRegistry.getPages());
      }
    });
  }
}
