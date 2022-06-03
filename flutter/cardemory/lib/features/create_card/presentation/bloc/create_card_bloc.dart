import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_event.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_state.dart';

class CreateCardBloc extends BaseBloc<CreateCardEvent, CreateCardState> {
  final NavBloc _navBloc;

  CreateCardBloc(this._navBloc) : super(CreateCardState.initial);

  @override
  Stream<CreateCardState> mapEventToState(CreateCardEvent event) async* {
    if (event is CreateCardSaveEvent) {
      // TODO validate title and description, show on ui
      // TODO save Card to repository
      _navBloc.add(NavEvent.pop);
    }
  }
}
