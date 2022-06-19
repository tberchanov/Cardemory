import 'package:cardemory/core/navigation/app_page.dart';
import 'package:equatable/equatable.dart';

abstract class NavEvent extends Equatable {
  static final pop = PopPage();
  static final resetToInitial = ResetToInitial();
  static final unregisterOnPopPage = UnregisterOnPopPage();

  static add(AppPage page) => AddPage(page);

  static registerOnPopPage(bool Function() onPopPage) => RegisterOnPopPage(onPopPage);

  @override
  List<Object?> get props => [];
}

class AddPage extends NavEvent {
  final AppPage page;

  AddPage(this.page);

  @override
  String toString() => "AddPage: $page";

  @override
  List<Object?> get props => [page];
}

class PopPage extends NavEvent {}

class ResetToInitial extends NavEvent {}

class RegisterOnPopPage extends NavEvent {
  final bool Function() onPopPage;

  RegisterOnPopPage(this.onPopPage);
}

class UnregisterOnPopPage extends NavEvent {}
