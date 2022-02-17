package com.cardemory.card_set_editor.mvp

import android.net.Uri
import com.cardemory.card_set_editor.mvp.CardSetEditorContract.Companion.SELECT_FILE_REQUEST_CODE
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.card_set_editor.ui.tutorial.CardSetEditorTutorialSpotlight
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.interactor.GetCardSetFromFileInteractor
import com.cardemory.carddata.interactor.SaveCardSetInteractor
import com.cardemory.carddata.interactor.failure.JsonSyntaxFailure
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CardSetEditorPresenter(
    private val cardSetEditorNavigation: CardSetEditorNavigation,
    private val saveCardSetInteractor: SaveCardSetInteractor,
    private val getCardSetFromFileInteractor: GetCardSetFromFileInteractor,
    private val readBooleanInteractor: ReadBooleanInteractor,
    private val writeBooleanInteractor: WriteBooleanInteractor,
    override val cardSetEditorTutorialSpotlight: CardSetEditorTutorialSpotlight
) : BasePresenter<CardSetEditorContract.View>(),
    CardSetEditorContract.Presenter {

    override fun attachView(view: CardSetEditorContract.View) {
        super.attachView(view)
        if (!view.isEditMode()) {
            checkPreviousVisit()
        }
    }

    override fun detachView(finishing: Boolean) {
        view?.hideKeyboard()
        super.detachView(finishing)
    }

    private fun checkPreviousVisit() = launch(Dispatchers.IO) {
        val showTutorial = readBooleanInteractor.run(SHOW_TUTORIAL_KEY).valueRight
        if (showTutorial) {
            val previouslyVisited =
                readBooleanInteractor.run(PREVIOUS_VISIT_CREATE_CARD_SET_KEY).valueRight
            if (!previouslyVisited) {
                withContext(Dispatchers.Main) {
                    view?.showTutorialImport()
                }
                writeBooleanInteractor.run(
                    WriteBooleanInteractor.Params(PREVIOUS_VISIT_CREATE_CARD_SET_KEY, true)
                )
            }
        }
    }

    override fun onSaveCardSetClicked(cardSet: CardSet) {
        if (isCardSetValid(cardSet)) {
            saveCardSetInteractor(cardSet) {
                it.either(::onSaveCardSetFailure, ::onSaveCardSetSuccess)
            }
        }
    }

    private fun isCardSetValid(cardSet: CardSet) =
        if (cardSet.name.isEmpty()) {
            view?.showEmptyNameError()
            false
        } else {
            true
        }

    private fun onSaveCardSetFailure(f: Failure) {
        Timber.e("onSaveCardSetFailure: $f")
    }

    private fun onSaveCardSetSuccess(cardSet: CardSet) {
        Timber.d("onSaveCardSetSuccess: $cardSet")
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
        private const val PREVIOUS_VISIT_CREATE_CARD_SET_KEY = "PREVIOUS_VISIT_CREATE_CARD_SET"
        private const val SHOW_TUTORIAL_KEY = "SHOW_TUTORIAL"
        private const val SELECTOR_FILE_FILTER = "text/plain"
    }
}
