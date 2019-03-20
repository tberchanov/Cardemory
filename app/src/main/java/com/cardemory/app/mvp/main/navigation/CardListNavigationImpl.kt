package com.cardemory.app.mvp.main.navigation

import com.cardemory.carddata.entity.Card
import com.cardemory.cardeditor.navigation.CardEditorScreen
import com.cardemory.cardlist.navigation.CardListNavigation
import ru.terrakok.cicerone.Router

class CardListNavigationImpl(
    private val router: Router
) : CardListNavigation {

    override fun showCreateCardScreen() {
        router.navigateTo(CardEditorScreen())
    }

    override fun showEditCardScreen(card: Card) {
        router.navigateTo(CardEditorScreen(card))
    }
}