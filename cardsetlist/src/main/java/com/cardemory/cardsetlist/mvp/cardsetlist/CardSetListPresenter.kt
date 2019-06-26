package com.cardemory.cardsetlist.mvp.cardsetlist

import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardSetListPresenter(
    private val cardSetListNavigation: CardSetListNavigation,
    private val getAllCardSetsInteractor: GetAllCardSetsInteractor
) : BasePresenter<CardSetListContract.View>(),
    CardSetListContract.Presenter {

    override fun attachView(view: CardSetListContract.View) {
        super.attachView(view)
        getAllCardSetsInteractor(Unit) {
            it.either(::onGetAllCardSetsFailure, ::onGetAllCardSetsSuccess)
        }
    }

    private fun onGetAllCardSetsFailure(f: Failure) {
        Timber.e("onGetAllCardSetsFailure: $f")
    }

    private fun onGetAllCardSetsSuccess(cardSets: List<CardSet>) {
        Timber.d("onGetAllCardSetsSuccess: $cardSets")
        view?.showCardSets(cardSets)
    }

    override fun onCreateCardSetClicked() {
        cardSetListNavigation.showCreateCardSetScreen()
    }

    override fun onCardSetClicked(cardSet: CardSet) {
        cardSetListNavigation.showCardsScreen(cardSet)
    }

    override fun onPrivacyPolicyClicked() {
        cardSetListNavigation.showPrivacyPolicyScreen()
    }

    override fun onEditCardSetClicked(cardSet: CardSet) {
        cardSetListNavigation.showEditCardSetScreen(cardSet)
    }

    override fun onDeleteCardSetClicked(cardSet: CardSet) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
