import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/domain/training/entity/training_data.dart';
import 'package:cardemory/presentation/training/bloc/training_bloc.dart';
import 'package:cardemory/presentation/training/bloc/training_event.dart';
import 'package:cardemory/presentation/training/bloc/training_state.dart';
import 'package:cardemory/presentation/training/widget/training_buttons_panel.dart';
import 'package:cardemory/presentation/training/widget/training_card.dart';
import 'package:flip_card/flip_card.dart';
import 'package:flip_card/flip_card_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';
import 'package:swipe_cards/swipe_cards.dart';

class PageTrainingFactory extends AppPageFactory {
  @override
  Future<AppPage?> build(RouteData routeData) async {
    if (routeData.route == "training") {
      // TODO load data for training by card set id
      // return PageTraining();
    }
    return null;
  }
}

class PageTraining extends AppPage {
  static final _log = Logger("PageTraining");
  final TrainingData trainingData;

  @override
  String get routeName => "/training";

  late final _matchEngine = MatchEngine(
    swipeItems: trainingData.cards.map((card) => SwipeItem(content: card)).toList(),
  );

  final _flipController = FlipCardController();

  PageTraining(this.trainingData);

  @override
  Widget buildChild() {
    return Scaffold(
      appBar: AppBar(title: Text("Training: ${trainingData.cardSet.name}")),
      body: BlocRenderer<TrainingBloc, TrainingState>(
        renderPage: (context, state) {
          final bloc = context.read<TrainingBloc>();
          return Column(
            children: [
              Expanded(
                child: SwipeCards(
                  matchEngine: _matchEngine,
                  itemBuilder: (context, index) => FlipCard(
                    speed: 1000,
                    controller: _flipController,
                    flipOnTouch: false,
                    front: TrainingCard(
                      text: trainingData.cards[index].title,
                      onTap: () => _flipController.toggleCard(),
                    ),
                    back: TrainingCard(
                      text: trainingData.cards[index].description,
                      onTap: () => _flipController.toggleCard(),
                    ),
                  ),
                  onStackFinished: () {
                    bloc.add(TrainingEventFinish());
                  },
                  itemChanged: (swipeItem, index) {
                    final decision = swipeItem.decision;
                    if (decision == Decision.like) {
                      bloc.add(TrainingEventKnow(swipeItem.content));
                    } else if (decision == Decision.nope) {
                      bloc.add(TrainingEventForgot(swipeItem.content));
                    } else {
                      _log.warning("Unexpected swipe decision: $decision");
                    }
                  },
                ),
                flex: 2,
              ),
              Expanded(
                flex: 1,
                child: TrainingButtonsPanel(
                  onForgotClick: () {
                    _log.info("onForgotClick: ${_matchEngine.currentItem?.content}");
                    _matchEngine.currentItem?.nope();
                  },
                  onKnowClick: () {
                    _log.info("onKnowClick: ${_matchEngine.currentItem?.content}");
                    _matchEngine.currentItem?.like();
                  },
                ),
              ),
            ],
          );
        },
      ),
    );
  }
}
