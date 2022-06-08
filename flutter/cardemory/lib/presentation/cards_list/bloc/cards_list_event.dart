abstract class CardsListEvent {
  static loadName(int cardSetId, String? initialName) => LoadNameEvent(cardSetId, initialName);

  static creteCard(int cardSetId) => CreateCardEvent(cardSetId);

  static loadCards(int cardSetId) => CardsListLoad(cardSetId);
  static final startTraining = StartTrainingEvent();
  static final trainingIsNotAvailableShown = TrainingIsNotAvailableShownEvent();
  static final messagesHidden = MessagesHiddenEvent();
}

class LoadNameEvent extends CardsListEvent {
  final int cardSetId;
  final String? initialName;

  LoadNameEvent(this.cardSetId, this.initialName);
}

class CardsListLoad extends CardsListEvent {
  final int cardSetId;

  CardsListLoad(this.cardSetId);
}

class CreateCardEvent extends CardsListEvent {
  final int cardSetId;

  CreateCardEvent(this.cardSetId);
}

class StartTrainingEvent extends CardsListEvent {}

class TrainingIsNotAvailableShownEvent extends CardsListEvent {}

class MessagesHiddenEvent extends CardsListEvent {}
