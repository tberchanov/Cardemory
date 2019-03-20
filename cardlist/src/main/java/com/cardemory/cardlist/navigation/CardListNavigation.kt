package com.cardemory.cardlist.navigation

import com.cardemory.carddata.entity.Card

interface CardListNavigation {

    fun showCreateCardScreen()

    fun showEditCardScreen(card: Card)
}