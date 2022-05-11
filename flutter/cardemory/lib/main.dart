import 'package:cardemory/features/card_set_list/presentation/bloc/card_set_list_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'injection_container.dart' as di;

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await di.init();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: CardSetListPage(),
    );
  }
}

class CardSetListPage extends StatelessWidget {
  const CardSetListPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<CardSetListBloc>(
        future: di.getIt.getAsync<CardSetListBloc>(),
        builder: (_, asyncSnapshot) {
          if (asyncSnapshot.hasData) {
            return BlocProvider(
              create: (_) => asyncSnapshot.data!,
              child: BlocBuilder<CardSetListBloc, CardSetListState>(
                builder: (context, state) => _buildCardSetListPage(state),
              ),
            );
          } else {
            return Text("error");
          }
        },
      ),
    );
  }
}

Widget _buildCardSetListPage(CardSetListState state) {
  if (state is CardSetListEmpty) {
    return Text("Empty");
  } else {
    return Text("stub $state");
  }
}
