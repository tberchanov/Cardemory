package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract

interface CardListContract {

    interface View : BaseContract.View {

        fun showCards(cards: List<Card>)
    }

    interface Presenter : BaseContract.Presenter<View>
}