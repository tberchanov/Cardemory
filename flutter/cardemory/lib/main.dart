import 'dart:developer' as developer;

import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_route_information_parser.dart';
import 'package:cardemory/core/navigation/app_router_delegate.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'injection_container.dart' as di;

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  di.init();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  final routerDelegate = AppRouterDelegate(di.getIt.get(), di.getIt.get());

  @override
  Widget build(BuildContext context) {
    developer.log("build", name: "MyApp");
    return BlocProvider<NavBloc>.value(
      value: di.getIt.get(),
      child: BlocListener<NavBloc, List<AppPage>>(
        listener: (context, state) {
          developer.log("NavBloc builder", name: "MyApp");
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
