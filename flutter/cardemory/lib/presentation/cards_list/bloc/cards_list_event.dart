abstract class CardsListEvent {
  static loadName(int cardSetId) => LoadNameEvent(cardSetId);
  static creteCard(int cardSetId) => CreateCardEvent(cardSetId);
  static final loadCards = CardsListLoad();
}

class LoadNameEvent extends CardsListEvent {
  final int cardSetId;

  LoadNameEvent(this.cardSetId);
}

class CardsListLoad extends CardsListEvent {}

class CreateCardEvent extends CardsListEvent {
  final int cardSetId;

  CreateCardEvent(this.cardSetId);
}
