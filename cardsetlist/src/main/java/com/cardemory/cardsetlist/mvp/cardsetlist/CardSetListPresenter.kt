package com.cardemory.cardsetlist.mvp.cardsetlist

import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.DeleteCardSetsInteractor
import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardSetListPresenter(
    private val cardSetListNavigation: CardSetListNavigation,
    private val getAllCardSetsInteractor: GetAllCardSetsInteractor,
    private val deleteCardSetsInteractor: DeleteCardSetsInteractor
) : BasePresenter<CardSetListContract.View>(),
    CardSetListContract.Presenter {

    override fun attachView(view: CardSetListContract.View) {
        super.attachView(view)
        loadCardSets()
    }

    private fun loadCardSets() {
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
        view?.setSelectionModeEnabled(true)
        view?.selectCardSetForDeletion(cardSet)
    }

    override fun onDeleteCardSetsClicked(cardSets: List<CardSet>) {
        deleteCardSetsInteractor(cardSets) {
            it.either(::onCardSetsDeleteFailure, ::onCardSetsDeleteSuccess)
        }
    }

    private fun onCardSetsDeleteFailure(failure: Failure) {
        Timber.e("onCardSetsDeleteFailure: $failure")
    }

    private fun onCardSetsDeleteSuccess(cardSets: List<CardSet>) {
        view?.setSelectionModeEnabled(false)
        view?.clearCardSets(cardSets)
    }

    override fun onCardSetSelected(cardSet: CardSet, selected: Boolean) {
        Timber.d("onCardSetSelected $selected: $cardSet")
        if (view?.getSelectedItemsCount() == 0) {
            view?.setSelectionModeEnabled(false)
        } else {
            view?.showSelectionTitle()
        }
    }
}
