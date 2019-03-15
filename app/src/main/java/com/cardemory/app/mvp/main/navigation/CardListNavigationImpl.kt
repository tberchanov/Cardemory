package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardlist.navigation.CardListNavigation
import ru.terrakok.cicerone.Router
import timber.log.Timber

class CardListNavigationImpl(
    private val router: Router
) : CardListNavigation {

    override fun showCreateCardScreen() {
        Timber.e("showCreateCardScreen")
    }
}