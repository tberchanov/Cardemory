import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';

abstract class AppPage extends Equatable {
  late final _key = ValueKey(routeName);
  final Widget _child;

  AppPage(this._child);

  late final page = MaterialPage(child: _child, key: _key);

  @override
  List<Object> get props => [routeName];

  String get routeName;
}
