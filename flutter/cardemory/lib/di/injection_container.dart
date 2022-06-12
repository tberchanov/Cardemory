import 'package:cardemory/data/database_module.dart';
import 'package:cardemory/di/injection_container.config.dart';
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:get_it/get_it.dart';
import 'package:injectable/injectable.dart';

const envWeb = Environment('web');
const envMobile = Environment('mobile');

GetIt getIt = GetIt.instance;

@InjectableInit(
  initializerName: r'$initGetIt',
  preferRelativeImports: true,
  asExtension: false,
)
Future<void> configureDependencies() async {
  const Environment env = kIsWeb ? envWeb : envMobile;

  if (env == envMobile) {
    final database = await DatabaseModule.openDatabase();
    getIt.registerSingleton(database);
  }

  $initGetIt(getIt, environment: env.name);
  getIt.allReadySync();
}
