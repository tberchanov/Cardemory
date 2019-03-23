package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseContract

interface CardListContract {

    interface View : BaseContract.View {

        fun showCards(cards: List<Card>)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCreateCardClicked(cardSet: CardSet)

        fun onCardClicked(card: Card)

        fun showCards(cardSet: CardSet)
    }
}