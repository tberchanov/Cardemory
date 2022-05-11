import 'package:cardemory/core/db.dart';
import 'package:cardemory/features/card_set_list/domain/repositories/card_set_repository_impl.dart';
import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:get_it/get_it.dart';

import 'features/card_set_list/domain/repositories/card_set_repository.dart';
import 'features/card_set_list/domain/usecases/get_card_set_list.dart';

GetIt getIt = GetIt.instance;

Future<void> init() async {
  getIt.registerFactoryAsync(
    () async => CardSetListBloc(await getIt.getAsync()),
  );
  getIt.registerSingletonAsync(
    () async => GetCardSetList(await getIt.getAsync()),
  );
  getIt.registerSingletonAsync(() => DB.create());
  getIt.registerSingletonAsync<CardSetRepository>(
    () async => CardSetRepositoryImpl(await getIt.getAsync()),
  );
  await getIt.allReady();
}
