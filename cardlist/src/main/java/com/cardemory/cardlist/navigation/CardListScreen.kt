package com.cardemory.cardlist.navigation

import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.mvp.CardListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardListScreen(private val cardSet: CardSet) : SupportAppScreen() {

    override fun getFragment() = CardListFragment.newInstance(cardSet)
}
