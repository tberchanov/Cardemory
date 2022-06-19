import 'package:cardemory/core/base_bloc.dart';
import 'package:cardemory/core/navigation/nav_bloc.dart';
import 'package:cardemory/core/navigation/nav_event.dart';
import 'package:cardemory/core/usecases/usecase.dart';
import 'package:cardemory/domain/card_set/entity/card_set.dart';
import 'package:cardemory/domain/card_set/usecase/get_card_set_list_use_case.dart';
import 'package:cardemory/presentation/card_set_list/page_card_set_list.dart';
import 'package:cardemory/presentation/cards_list/page_cards_list.dart';
import 'package:equatable/equatable.dart';
import 'package:injectable/injectable.dart';
import 'package:logging/logging.dart';

part 'card_set_list_event.dart';
part 'card_set_list_state.dart';

@injectable
class CardSetListBloc extends BaseBloc<CardSetListEvent, CardSetListState> {
  final _log = Logger('CardSetListBloc');
  final GetCardSetListUseCase _getCardSetList;
  final NavBloc _navBloc;

  CardSetListBloc(this._getCardSetList, this._navBloc) : super(CardSetListInitial()) {
    _navBloc.stream.forEach((pages) {
      if (pages.last is PageCardSetList) {
        add(CardSetListLoad());
      }
    });
  }

  @override
  Stream<CardSetListState> mapEventToState(CardSetListEvent event) async* {
    if (event is CardSetListLoad) {
      yield* _onCardSetListLoad();
    } else if (event is CardSetClick) {
      _navBloc.add(AddPage(PageCardsList.fromCardSet(event.cardSet)));
    }
  }

  Stream<CardSetListState> _onCardSetListLoad() async* {
    yield CardSetListLoading();
    final cardSetsEither = await _getCardSetList(NoParams());
    yield cardSetsEither.fold(
      (failure) {
        _log.warning("getCardSetList failure: $failure");
        return CardSetListError();
      },
      (cardSets) {
        if (cardSets.isEmpty) {
          return CardSetListEmpty();
        } else {
          return CardSetList(cardSets);
        }
      },
    );
  }
}
