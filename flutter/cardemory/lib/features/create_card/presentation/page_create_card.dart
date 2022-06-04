import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/core/widgets/card_text_field.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_bloc.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_event.dart';
import 'package:cardemory/features/create_card/presentation/bloc/create_card_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class PageCreateCardFactory extends AppPageFactory {
  @override
  AppPage? build(String route) {
    if (route == "create-card") {
      return PageCreateCard();
    } else {
      return null;
    }
  }
}

class PageCreateCard extends AppPage {
  @override
  String get routeName => "/create-card";

  final _titleTextController = TextEditingController();
  final _descriptionTextController = TextEditingController();

  @override
  Widget buildChild() {
    return BlocRenderer<CreateCardBloc, CreateCardState>((state, context) {
      return Scaffold(
        appBar: AppBar(title: const Text("Create card")),
        body: Column(
          children: [
            CardTextField(
              "Title",
              _titleTextController,
              errorText: _getTitleTextError(state),
            ),
            CardTextField(
              "Description",
              _descriptionTextController,
              errorText: _getDescriptionTextError(state),
            ),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            context.read<CreateCardBloc>().add(CreateCardEvent.save(
                  _titleTextController.text,
                  _descriptionTextController.text,
                ));
          },
          child: const Icon(Icons.add),
        ),
      );
    });
  }

  String? _getTitleTextError(CreateCardState state) {
    if (state is InvalidFieldState) {
      return state.titleMessage;
    }
    return null;
  }

  String? _getDescriptionTextError(CreateCardState state) {
    if (state is InvalidFieldState) {
      return state.descriptionMessage;
    }
    return null;
  }
}
