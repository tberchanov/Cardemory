abstract class CardsListEvent {
  static loadName(int cardSetId) => LoadNameEvent(cardSetId);
  static final creteCard = CreateCardEvent();
}

class LoadNameEvent extends CardsListEvent {
  final int cardSetId;

  LoadNameEvent(this.cardSetId);
}

class CreateCardEvent extends CardsListEvent {}
