package com.cardemory.train.mvp

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract
import com.cardemory.train.ui.model.TrainCard
import com.cardemory.train.ui.tutorial.TrainTutorialSpotlight
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

        fun showCardContent(trainCard: TrainCard)

        fun showTutorial()
    }

    interface Presenter : BaseContract.Presenter<View> {

        val trainTutorialSpotlight: TrainTutorialSpotlight

        fun onCardRemembered(card: Card)

        fun onCardForgot(card: Card)

        fun onLastCardSwiped(cardsList: List<Card>)

        fun onFinishMessageConfirmed()

        fun onBackClicked()

        fun onBackMessageConfirmed()

        fun onTrainCardLongPressed(trainCard: TrainCard)
    }
}