import 'package:cardemory/injection_container.dart' as di;
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class BlocRenderer<B extends StateStreamableSource<S>, S>
    extends StatelessWidget {
  final Widget Function(S state) _renderPage;

  const BlocRenderer(this._renderPage);

  @override
  Widget build(BuildContext context) {
    return BlocProvider<B>.value(
      value: di.getIt.get(),
      child: BlocBuilder<B, S>(
        builder: (context, state) => _renderPage(state),
      ),
    );
  }
}
