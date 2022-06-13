import 'package:cardemory/di/injection_container.dart' as di;
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class BlocRenderer<B extends StateStreamableSource<S>, S> extends StatelessWidget {
  final Widget Function(BuildContext context, S state) renderPage;
  final void Function(BuildContext context, S state)? onListenState;
  final bool Function(S previous, S current)? listenWhen;
  final B? bloc;

  const BlocRenderer({
    Key? key,
    required this.renderPage,
    this.onListenState,
    this.listenWhen,
    this.bloc,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BlocProvider<B>.value(
      value: bloc ?? di.getIt.get(),
      child: BlocConsumer<B, S>(
        builder: (context, state) => renderPage(context, state),
        listener: onListenState ?? (_, __) {},
        listenWhen: listenWhen,
      ),
    );
  }
}
