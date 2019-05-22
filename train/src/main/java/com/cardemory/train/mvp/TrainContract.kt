package com.cardemory.train.mvp

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract
import com.cardemory.train.ui.widget.StarState

interface TrainContract {

    interface View : BaseContract.View {

        fun showFinishMessage(
            @StringRes finishTrainDialogTitleRes: Int,
            @StringRes finishTrainDialogMessageRes: Int,
            @ColorRes textColorRes: Int,
            vararg starState: StarState
        )

        fun showBackMessage()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCardRemembered(card: Card)

        fun onCardForgot(card: Card)

        fun onLastCardSwiped(cardsList: List<Card>)

        fun onFinishMessageConfirmed()

        fun onBackClicked()

        fun onBackMessageConfirmed()
    }
}