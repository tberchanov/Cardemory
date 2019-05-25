package com.cardemory.card_set_editor.mvp

import android.net.Uri
import com.cardemory.card_set_editor.mvp.CardSetEditorContract.Companion.SELECT_FILE_REQUEST_CODE
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.GetCardSetFromFileInteractor
import com.cardemory.carddata.interactor.SaveCardSetInteractor
import com.cardemory.carddata.interactor.failure.JsonSyntaxFailure
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber

class CardSetEditorPresenter(
    private val cardSetEditorNavigation: CardSetEditorNavigation,
    private val saveCardSetInteractor: SaveCardSetInteractor,
    private val getCardSetFromFileInteractor: GetCardSetFromFileInteractor
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

    override fun selectFile() {
        cardSetEditorNavigation.showSelectFileScreen(
            SELECT_FILE_REQUEST_CODE,
            SELECTOR_FILE_FILTER
        )
    }

    override fun onImportFileSelected(fileUri: Uri) {
        getCardSetFromFileInteractor(fileUri) {
            it.either(::onGetCardSetFromFileFailure, ::onGetCardSetFromFileSuccess)
        }
    }

    private fun onGetCardSetFromFileSuccess(cardSet: CardSet) {
        view?.showCardSet(cardSet)
    }

    private fun onGetCardSetFromFileFailure(failure: Failure) {
        if (failure is JsonSyntaxFailure) {
            view?.showInvalidFileContentsMessage()
        } else {
            Timber.e("onGetCardSetFromFileFailure: $failure")
        }
    }

    companion object {
        private const val SELECTOR_FILE_FILTER = "text/plain"
    }
}
