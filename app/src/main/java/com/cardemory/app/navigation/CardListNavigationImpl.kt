package com.cardemory.app.navigation

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardeditor.navigation.CardEditorScreen
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.navigation.AppRouter
import com.cardemory.train.navigation.TrainScreen

class CardListNavigationImpl(
    private val router: AppRouter
) : CardListNavigation {

    override fun showCreateCardScreen(cardSet: CardSet) {
        router.navigateForResult(CardEditorScreen(cardSet), CARD_EDITOR_REQUEST)
    }

    override fun showEditCardScreen(card: Card) {
        router.navigateForResult(CardEditorScreen(card), CARD_EDITOR_REQUEST)
    }

    override fun showTrainScreen() {
        router.navigateTo(TrainScreen())
    }

    companion object {
        private const val CARD_EDITOR_REQUEST = 1
    }
}
