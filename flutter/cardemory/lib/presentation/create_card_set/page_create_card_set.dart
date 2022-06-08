import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/core/widgets/card_text_field.dart';
import 'package:cardemory/core/widgets/loader.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_bloc.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_event.dart';
import 'package:cardemory/presentation/create_card_set/bloc/create_card_set_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class PageCreateCardSetFactory extends AppPageFactory {
  @override
  AppPage? build(RouteData routeData) {
    if (routeData.route == "create-card-set") {
      return PageCreateCardSet();
    } else {
      return null;
    }
  }
}

class PageCreateCardSet extends AppPage {
  static final _log = Logger("PageCreateCardSet");

  // TODO need to call dispose for controllers?
  final _nameTextController = TextEditingController();

  @override
  String get routeName => "/create-card-set";

  @override
  Widget buildChild() {
    return BlocRenderer<CreateCardSetBloc, CreateCardSetState>(
      (context, state) {
        _log.info("State: $state");
        return Stack(
          children: [
            Scaffold(
              appBar: AppBar(
                title: const Text("Create card set"),
              ),
              body: Column(
                children: [
                  CardTextField("Name", _nameTextController),
                ],
              ),
              floatingActionButton: FloatingActionButton(
                child: const Icon(Icons.add),
                onPressed: () {
                  _log.info("onPressed add: ${_nameTextController.value.text}");
                  context
                      .read<CreateCardSetBloc>()
                      .add(CreateCardSetEvent.create(_nameTextController.text));
                },
              ),
            ),
            if (state is CreateCardSetLoading) const Loader(),
          ],
        );
      },
    );
  }
}
