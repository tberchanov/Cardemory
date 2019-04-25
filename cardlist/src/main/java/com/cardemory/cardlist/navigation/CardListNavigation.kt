package com.cardemory.cardlist.navigation

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet

interface CardListNavigation {

    fun showCreateCardScreen(cardSet: CardSet)

    fun showEditCardScreen(card: Card)

    fun showTrainScreen()
}