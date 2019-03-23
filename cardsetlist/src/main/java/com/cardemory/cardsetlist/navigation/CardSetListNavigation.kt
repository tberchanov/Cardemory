package com.cardemory.cardsetlist.navigation

import com.cardemory.carddata.entity.CardSet

interface CardSetListNavigation {

    fun showCreateCardSetScreen()

    fun showEditCardSetScreen(cardSet: CardSet)

    fun showCardsScreen(cardSet: CardSet)
}
