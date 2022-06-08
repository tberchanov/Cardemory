import 'package:cardemory/domain/card/entity/card.dart';
import 'package:equatable/equatable.dart';

class CardsListState extends Equatable {
  final String? cardSetName;
  final List<Card>? cards;
  final bool cardSetNotFound;
  final bool failure;
  final bool isTrainingAvailable;
  final String? trainingIsNotAvailableMessage;
  final bool hideMessages;

  const CardsListState(
    this.cardSetName,
    this.cards,
    this.cardSetNotFound,
    this.failure,
    this.isTrainingAvailable,
    this.trainingIsNotAvailableMessage,
    this.hideMessages,
  );

  const CardsListState.initial()
      : cardSetName = null,
        cards = null,
        cardSetNotFound = false,
        failure = false,
        isTrainingAvailable = false,
        trainingIsNotAvailableMessage = null,
        hideMessages = false;

  CardsListState copyWith({
    String? cardSetName,
    List<Card>? cards,
    bool? cardSetNotFound,
    bool? failure,
    bool? isTrainingAvailable,
    String? trainingIsNotAvailableMessage,
    bool? hideMessages,
  }) =>
      CardsListState(
        cardSetName ?? this.cardSetName,
        cards ?? this.cards,
        cardSetNotFound ?? this.cardSetNotFound,
        failure ?? this.failure,
        isTrainingAvailable ?? this.isTrainingAvailable,
        trainingIsNotAvailableMessage ?? this.trainingIsNotAvailableMessage,
        hideMessages ?? this.hideMessages,
      );

  @override
  List<Object> get props => [
        cardSetName ?? "",
        cards ?? [],
        cardSetNotFound,
        failure,
        isTrainingAvailable,
        trainingIsNotAvailableMessage ?? "",
        hideMessages,
      ];
}
