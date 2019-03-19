package com.cardemory.cardeditor

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardEditorPresenter(
    private val cardEditorNavigation: CardEditorNavigation,
    private val saveCardInteractor: SaveCardInteractor
) : BasePresenter<CardEditorContract.View>(),
    CardEditorContract.Presenter {

    override fun onSaveCardClicked(card: Card) {
        saveCardInteractor(card) {
            it.either(::onSaveCardFailure, ::onSaveCardSuccess)
        }
    }

    private fun onSaveCardFailure(f: Failure) {
        Timber.e("onSaveCardFailure: $f")
    }

    private fun onSaveCardSuccess(card: Card) {
        Timber.d("onSaveCardSuccess: $card")
        view?.hideKeyboard()
        cardEditorNavigation.closeScreen()
    }
}
