package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardlist.navigation.CardListScreen
import ru.terrakok.cicerone.Router

class MainNavigationImpl(
    private val router: Router
) : MainNavigation {

    override fun showCardList() {
        router.newRootScreen(CardListScreen())
    }

    override fun back() {
        router.exit()
    }
}