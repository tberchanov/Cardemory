package com.cardemory.cardlist

import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardListScreen : SupportAppScreen() {

    override fun getFragment() = CardListFragment.newInstance()
}