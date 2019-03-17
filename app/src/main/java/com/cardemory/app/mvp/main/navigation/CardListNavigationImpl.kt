package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardeditor.CardEditorScreen
import com.cardemory.cardlist.navigation.CardListNavigation
import ru.terrakok.cicerone.Router

class CardListNavigationImpl(
    private val router: Router
) : CardListNavigation {

    override fun showCreateCardScreen() {
        router.navigateTo(CardEditorScreen())
    }
}