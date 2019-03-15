package com.cardemory.cardlist.navigation

import com.cardemory.cardlist.mvp.CardListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardListScreen : SupportAppScreen() {

    override fun getFragment() = CardListFragment.newInstance()
}