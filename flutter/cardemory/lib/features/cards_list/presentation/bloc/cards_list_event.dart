abstract class CardsListEvent {
  static loadName(int cardSetId) => LoadName(cardSetId);
}

class LoadName extends CardsListEvent {
  final int cardSetId;

  LoadName(this.cardSetId);
}
