package com.cardemory.cardsetlist.mvp

import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.mvp.BasePresenter

class CardSetListPresenter(
    private val cardSetListNavigation: CardSetListNavigation,
    private val getAllCardSetsInteractor: GetAllCardSetsInteractor
) : BasePresenter<CardSetListContract.View>(),
    CardSetListContract.Presenter {

    override fun onCreateCardSetClicked() {
        cardSetListNavigation.showCreateCardSetScreen()
    }
}
