package com.cardemory.train.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract

interface TrainContract {

    interface View : BaseContract.View {

        fun showFinishMessage(resultMemoryRank: Double)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCardRemembered(card: Card)

        fun onCardForgot(card: Card)

        fun onLastCardSwiped(cardsList: List<Card>)

        fun onFinishMessageConfirmed()
    }
}