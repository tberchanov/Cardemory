package com.cardemory.app.mvp.main.navigation

import com.cardemory.cardlist.CardListNavigation
import ru.terrakok.cicerone.Router
import timber.log.Timber

class CardListNavigationImpl(
    private val router: Router
) : CardListNavigation {

    override fun showCreateCardScreen() {
        Timber.e("showCreateCardScreen")
    }
}