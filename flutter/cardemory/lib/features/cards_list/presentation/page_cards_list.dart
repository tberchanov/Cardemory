import 'package:cardemory/core/navigation/app_page.dart';
import 'package:cardemory/core/navigation/app_page_factory.dart';
import 'package:cardemory/features/card_set_list/domain/entities/card_set.dart';
import 'package:flutter/material.dart';

class PageCardsListFactory extends AppPageFactory {
  final RegExp _regExp = RegExp(r'cards-.+');
  final _prefixLength = 6; // "cards-"

  @override
  AppPage? build(String route) {
    if (_regExp.hasMatch(route)) {
      final cardSetId = int.tryParse(route.substring(_prefixLength));
      if (cardSetId != null) {
        return PageCardsList(cardSetId);
      }
    }
    return null;
  }
}

class PageCardsList extends AppPage {
  final int _cardSetId;
  final String? _cardSetName;

  PageCardsList(this._cardSetId) : _cardSetName = null;

  PageCardsList.fromCardSet(CardSet cardSet)
      : _cardSetId = cardSet.id,
        _cardSetName = cardSet.name;

  @override
  String get routeName => "/cards-$_cardSetId";

  @override
  Widget buildChild() {
    return Scaffold(
      appBar: AppBar(title: Text(_cardSetName ?? _cardSetId.toString())),
    );
  }
}
