package com.cardemory.cardsetlist.mvp.cardsetlist

import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseContract

interface CardSetListContract {

    interface View : BaseContract.View {

        fun showCardSets(cardSets: List<CardSet>)

        fun selectCardSetForDeletion(cardSet: CardSet)

        fun setSelectionModeEnabled(enabled: Boolean)

        fun getSelectedItemsCount(): Int

        fun showSelectionTitle()

        fun clearCardSets(cardSets: List<CardSet>)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCreateCardSetClicked()

        fun onCardSetClicked(cardSet: CardSet)

        fun onPrivacyPolicyClicked()

        fun onEditCardSetClicked(cardSet: CardSet)

        fun onDeleteCardSetClicked(cardSet: CardSet)

        fun onDeleteCardSetsClicked(cardSets: List<CardSet>)

        fun onCardSetSelected(cardSet: CardSet, selected: Boolean)
    }
}