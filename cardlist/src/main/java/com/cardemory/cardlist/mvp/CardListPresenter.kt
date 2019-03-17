package com.cardemory.cardlist.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.GetAllCardsInteractor
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardListPresenter(
    private val navigation: CardListNavigation,
    private val getAllCardsInteractor: GetAllCardsInteractor
) : BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    override fun attachView(view: CardListContract.View) {
        super.attachView(view)

        getAllCardsInteractor(Unit) {
            it.either(
                ::onGetAllCardsFailure,
                ::onGetAllCardsSuccess
            )
        }
    }

    private fun onGetAllCardsFailure(f: Failure) {
        Timber.e("onGetAllCardsFailure: $f")
    }

    private fun onGetAllCardsSuccess(cards: List<Card>) {
        view?.showCards(cards)
    }

    override fun onCreateCardClicked() {
        navigation.showCreateCardScreen()
    }
}