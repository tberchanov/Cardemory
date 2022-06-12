import 'dart:developer' as developer;

import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_route_information_parser.dart';
import 'package:cardemory/core/navigation/app_router_delegate.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

import 'di/injection_container.dart' as di;

void main() async {
  Logger.root.level = Level.ALL;
  Logger.root.onRecord.listen((record) {
    developer.log(
      record.message,
      name: record.loggerName,
      time: record.time,
      level: record.level.value,
    );
  });
  WidgetsFlutterBinding.ensureInitialized();
  await di.configureDependencies();
  // di.init();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  final _log = Logger('MyApp');

  MyApp({Key? key}) : super(key: key);

  final AppRouterDelegate routerDelegate = di.getIt.get();

  @override
  Widget build(BuildContext context) {
    _log.info("build");
    return BlocProvider<NavBloc>.value(
      value: di.getIt.get(),
      child: BlocListener<NavBloc, List<AppPage>>(
        listener: (context, state) {
          _log.info("NavBloc listener");
          routerDelegate.refresh();
        },
        child: MaterialApp.router(
          routeInformationParser: AppRouteInformationParser(),
          routerDelegate: routerDelegate,
          title: 'Flutter Demo',
          theme: ThemeData(primarySwatch: Colors.blue),
        ),
      ),
    );
  }
}
