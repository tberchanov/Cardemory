package com.cardemory.cardsetlist.navigation

import com.cardemory.cardsetlist.mvp.CardSetListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardSetListScreen : SupportAppScreen() {

    override fun getFragment() = CardSetListFragment.newInstance()
}