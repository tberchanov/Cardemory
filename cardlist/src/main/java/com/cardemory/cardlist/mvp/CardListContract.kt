package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.BuildConfig
import com.cardemory.cardlist.ui.tutorial.CardListTutorialSpotlight
import com.cardemory.common.mvp.BaseContract
import com.cardemory.memory_label.CardMemoryLabelTransformer

interface CardListContract {

    interface View : BaseContract.View {

        fun showNewCard(card: Card)

        fun showNotEnoughCardsMessage(neededCardsCount: Int)

        fun showCardSetData(cardSet: CardSet)

        fun showSuccessExportingMessage(exportedFilePath: String)

        fun showExportDeniedPermissionMessage()

        fun setSelectionModeEnabled(enabled: Boolean)

        fun selectCardForDeletion(card: Card)

        fun getSelectedItemsCount(): Int

        fun showSelectionTitle()

        fun clearCards(cards: List<Card>)

        fun showTutorial()
    }

    interface Presenter : BaseContract.Presenter<View> {

        val cardListTutorialSpotlight: CardListTutorialSpotlight

        val cardMemoryLabelTransformer: CardMemoryLabelTransformer

        fun onCreateCardClicked(cardSet: CardSet)

        fun onCardClicked(card: Card)

        fun onCardSaved(card: Card)

        fun onTrainClicked(cardSet: CardSet)

        fun loadCardSet(cardSet: CardSet)

        fun exportCardSet(cardSet: CardSet)

        fun onPermissionDenied()

        fun showAppSettings()

        fun onDeleteCardClicked(card: Card)

        fun onCardSelected(card: Card, selected: Boolean)

        fun onDeleteCardsClicked(cards: List<Card>)
    }

    companion object {
        val REQUIRED_CARDS_FOR_TRAIN = if (BuildConfig.DEBUG) 10 else 10
    }
}