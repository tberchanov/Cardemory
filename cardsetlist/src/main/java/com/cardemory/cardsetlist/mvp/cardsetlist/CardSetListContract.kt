package com.cardemory.cardsetlist.mvp.cardsetlist

import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseContract

interface CardSetListContract {

    interface View : BaseContract.View {

        fun showCardSets(cardSets: List<CardSet>)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onCreateCardSetClicked()

        fun onCardSetClicked(cardSet: CardSet)

        fun onPrivacyPolicyClicked()
    }
}