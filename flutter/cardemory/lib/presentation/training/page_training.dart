import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/domain/training/entity/training_data.dart';
import 'package:cardemory/presentation/cards_list/widget/training_button.dart';
import 'package:cardemory/presentation/training/widget/training_answer_button.dart';
import 'package:flutter/material.dart';
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
  final TrainingData trainingData;

  @override
  String get routeName => "/training";

  PageTraining(this.trainingData);

  final MatchEngine matchEngine = MatchEngine();

  @override
  Widget buildChild() {
    return Scaffold(
      appBar: AppBar(title: Text("Training: stub")),
      body: Column(
        children: [
          Expanded(
            child: SwipeCards(
              matchEngine: matchEngine,
              itemBuilder: (context, index) => Text("Item $index"),
              onStackFinished: () {
                // TODO process finish
              },
            ),
            flex: 2,
          ),
          Expanded(
            flex: 1,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Spacer(flex: 1),
                Expanded(
                  flex: 7,
                  child: TrainingAnswerButton(
                    () {},
                    "Forgot",
                    Colors.red,
                  ),
                ),
                Spacer(flex: 1),
                Expanded(
                  flex: 7,
                  child: TrainingAnswerButton(
                    () {},
                    "Know",
                    Colors.green,
                  ),
                ),
                Spacer(flex: 1),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
