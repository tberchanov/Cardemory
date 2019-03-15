package com.cardemory.cardlist

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.GetAllCardsInteractor
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

        navigation.showCreateCardScreen()

        getAllCardsInteractor(Unit) {
            it.either(
                ::onGetAllCardsFailure,
                ::onGetAllCardsSuccess
            )
        }
    }

    private fun onGetAllCardsFailure(f: Failure) {
        // none
    }

    private fun onGetAllCardsSuccess(cards: List<Card>) {
        Timber.e("Cards: $cards")
    }
}