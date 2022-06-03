import 'package:flutter_bloc/flutter_bloc.dart';

abstract class BaseBloc<Event, State> extends Bloc<Event, State> {
  BaseBloc(State initialState) : super(initialState) {
    on<Event>((event, emit) async {
      await for (final state in mapEventToState(event)) {
        emit.call(state);
      }
    });
  }

  Stream<State> mapEventToState(Event event);
}
