package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.mvp.BasePresenter

class CardListPresenter(
    private val navigation: CardListNavigation
) : BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    override fun onCreateCardClicked(cardSet: CardSet) {
        navigation.showCreateCardScreen(cardSet)
    }

    override fun onCardClicked(card: Card) {
        navigation.showEditCardScreen(card)
    }

    override fun showCards(cardSet: CardSet) {
        val cards = cardSet.cards.values.toList()
        view?.showCards(cards)
    }

    override fun onCardSaved(card: Card) {
        view?.showNewCard(card)
    }

    override fun onTrainClicked(cardSet: CardSet) {
        navigation.showTrainScreen(cardSet)
    }
}