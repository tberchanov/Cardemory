import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/core/widgets/loader.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_bloc.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_event.dart';
import 'package:cardemory/features/create_card_set/presentation/bloc/create_card_set_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class PageCreateCardSetFactory extends AppPageFactory {
  @override
  AppPage? build(String route) {
    if (route == "card-set-create") {
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
  String get routeName => "/card-set-create";

  @override
  Widget buildChild() {
    return BlocRenderer<CreateCardSetBloc, CreateCardSetState>(
      (state, context) {
        _log.info("State: $state");
        return Stack(
          children: [
            Scaffold(
              appBar: AppBar(
                title: const Text("Create card set"),
              ),
              body: Column(
                children: [
                  _TextField("Name", _nameTextController),
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

class _TextField extends StatelessWidget {
  final String labelText;
  final TextEditingController controller;

  const _TextField(this.labelText, this.controller, {Key? key})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(10.0),
      child: TextField(
        autofocus: true,
        controller: controller,
        decoration: InputDecoration(
          border: const OutlineInputBorder(),
          labelText: labelText,
        ),
      ),
    );
  }
}
