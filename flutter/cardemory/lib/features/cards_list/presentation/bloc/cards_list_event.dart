abstract class CardsListEvent {
  static loadName(int cardSetId) => LoadNameEvent(cardSetId);
  static creteCard(int cardSetId) => CreateCardEvent(cardSetId);
}

class LoadNameEvent extends CardsListEvent {
  final int cardSetId;

  LoadNameEvent(this.cardSetId);
}

class CreateCardEvent extends CardsListEvent {
  final int cardSetId;

  CreateCardEvent(this.cardSetId);
}
