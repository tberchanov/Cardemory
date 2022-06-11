import 'package:cardemory/core/extension/string_ext.dart';
import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/core/widgets/bloc_renderer.dart';
import 'package:cardemory/core/widgets/card_text_field.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_bloc.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_event.dart';
import 'package:cardemory/presentation/create_card/bloc/create_card_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:logging/logging.dart';

class PageCreateCardFactory extends AppPageFactory {
  static final _log = Logger("PageCreateCardFactory");

  @override
  Future<AppPage?> build(RouteData routeData) async {
    if (routeData.route == "create-card") {
      final segments = routeData.uri.pathSegments;
      if (routeData.routeIndex >= 1 && segments.length >= 2) {
        // Path example: "/cards-0/create-card"
        final cardSetId = segments[routeData.routeIndex - 1].substringAfter("-")?.tryInt();
        if (cardSetId != null) {
          return PageCreateCard(cardSetId);
        }
      }
      _log.warning("Cannot parse: $routeData");
    }
    return null;
  }
}

class PageCreateCard extends AppPage {
  @override
  String get routeName => "/create-card";

  final _titleTextController = TextEditingController();
  final _descriptionTextController = TextEditingController();
  final int _cardSetId;

  PageCreateCard(this._cardSetId);

  @override
  Widget buildChild() {
    return BlocRenderer<CreateCardBloc, CreateCardState>(renderPage: (context, state) {
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
                  _cardSetId,
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
