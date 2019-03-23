package com.cardemory.cardeditor.navigation

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardeditor.mvp.CardEditorFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class CardEditorScreen private constructor() : SupportAppScreen() {

    private lateinit var card: Card
    private lateinit var cardSet: CardSet

    constructor(card: Card) : this() {
        this.card = card
    }

    constructor(cardSet: CardSet) : this() {
        this.cardSet = cardSet
    }

    override fun getFragment() =
        if (::card.isInitialized) {
            CardEditorFragment.newInstance(card)
        } else {
            CardEditorFragment.newInstance(cardSet)
        }
}