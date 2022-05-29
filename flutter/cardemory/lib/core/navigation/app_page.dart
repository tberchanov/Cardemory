import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

abstract class AppPage extends Equatable {
  late final _key = ValueKey(routeName);

  late final page = MaterialPage(child: buildChild(), key: _key);

  @override
  List<Object> get props => [routeName];

  String get routeName;

  Widget buildChild();
}
