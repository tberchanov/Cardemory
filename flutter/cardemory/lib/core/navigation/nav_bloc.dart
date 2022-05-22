import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/navigation_registry.dart';
import 'package:cardemory/features/card_set_list/presentation/page_card_set_list.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'dart:developer' as developer;

abstract class NavEvent extends Equatable {}

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
  List<Object?> get props => [this];
}

class NavBloc extends Bloc<NavEvent, List<AppPage>> {
  final NavigationRegistry navRegistry;

  NavBloc(this.navRegistry) : super(navRegistry.getPages()) {
    on<NavEvent>((event, emit) async {
      developer.log("On event: $event", name: "NavBloc");

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
