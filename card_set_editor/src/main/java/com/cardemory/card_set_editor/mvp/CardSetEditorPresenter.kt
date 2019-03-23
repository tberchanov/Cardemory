package com.cardemory.card_set_editor.mvp

import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.SaveCardSetInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardSetEditorPresenter(
    private val cardSetEditorNavigation: CardSetEditorNavigation,
    private val saveCardSetInteractor: SaveCardSetInteractor
) : BasePresenter<CardSetEditorContract.View>(),
    CardSetEditorContract.Presenter {

    override fun onSaveCardSetClicked(cardSet: CardSet) {
        saveCardSetInteractor(cardSet) {
            it.either(::onSaveCardSetFailure, ::onSaveCardSetSuccess)
        }
    }

    private fun onSaveCardSetFailure(f: Failure) {
        Timber.e("onSaveCardSetFailure: $f")
    }

    private fun onSaveCardSetSuccess(cardSet: CardSet) {
        Timber.d("onSaveCardSetSuccess: $cardSet")
        view?.hideKeyboard()
        cardSetEditorNavigation.closeScreen()
    }
}
