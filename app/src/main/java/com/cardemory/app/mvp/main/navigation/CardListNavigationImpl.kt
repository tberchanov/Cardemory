package com.cardemory.app.mvp.main.navigation

import android.util.Log
import com.cardemory.cardlist.CardListNavigation
import ru.terrakok.cicerone.Router

class CardListNavigationImpl(
    private val router: Router
) : CardListNavigation {

    override fun showCreateCardScreen() {
        Log.e("CardListNavigationImpl", "showCreateCardScreen")
    }
}