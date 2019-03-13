package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardlist.CardListScreen
import ru.terrakok.cicerone.Router

class MainNavigationImpl(
    private val router: Router
) : MainNavigation {

    override fun showCardList() {
        router.newRootScreen(CardListScreen())
    }
}