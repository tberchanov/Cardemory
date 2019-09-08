package com.cardemory.app.navigation

import com.cardemory.cardsetlist.navigation.CardSetListScreen
import ru.terrakok.cicerone.Router

class MainNavigationImpl(
    private val router: Router
) : MainNavigation {

    override fun showCardSetList() {
        router.newRootScreen(CardSetListScreen())
    }

    override fun back() {
        router.exit()
    }
}