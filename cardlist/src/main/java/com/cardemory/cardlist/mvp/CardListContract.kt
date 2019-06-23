package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.BuildConfig
import com.cardemory.common.mvp.BaseContract

interface CardListContract {

    interface View : BaseContract.View {

        fun showNewCard(card: Card)

        fun showNotEnoughCardsMessage(neededCardsCount: Int)

        fun showCardSetData(cardSet: CardSet)

        fun showSuccessExportingMessage(exportedFilePath: String)

        fun showExportDeniedPermissionMessage()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCreateCardClicked(cardSet: CardSet)

        fun onCardClicked(card: Card)

        fun onCardSaved(card: Card)

        fun onTrainClicked(cardSet: CardSet)

        fun loadCardSet(cardSet: CardSet)

        fun exportCardSet(cardSet: CardSet)

        fun onPermissionDenied()

        fun showAppSettings()
    }

    companion object {
        val REQUIRED_CARDS_FOR_TRAIN = if (BuildConfig.DEBUG) 1 else 10
    }
}