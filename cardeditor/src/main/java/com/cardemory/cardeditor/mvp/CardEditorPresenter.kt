package com.cardemory.cardeditor.mvp

import android.net.Uri
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.cardeditor.R
import com.cardemory.cardeditor.interactor.GetPhotoFileInteractor
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.cardeditor.ui.tutorial.CardEditorTutorialSpotlight
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.common.util.ProgressInteractorExecutor
import com.cardemory.infrastructure.entity.Failure
import com.cardemory.ocr.interactor.BaseRecognizeTextInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

class CardEditorPresenter(
    private val cardEditorNavigation: CardEditorNavigation,
    private val saveCardInteractor: SaveCardInteractor,
    private val getPhotoFileInteractor: GetPhotoFileInteractor,
    private val recognizeTextInteractor: BaseRecognizeTextInteractor,
    private val progressInteractorExecutor: ProgressInteractorExecutor,
    private val readBooleanInteractor: ReadBooleanInteractor,
    private val writeBooleanInteractor: WriteBooleanInteractor,
    override val cardEditorTutorialSpotlight: CardEditorTutorialSpotlight
) : BasePresenter<CardEditorContract.View>(),
    CardEditorContract.Presenter {

    private var cachedPhotoFile: File? = null

    override fun attachView(view: CardEditorContract.View) {
        super.attachView(view)
        checkPreviousVisit()
    }

    private fun checkPreviousVisit() = launch(Dispatchers.IO) {
        val showTutorial = readBooleanInteractor.run(SHOW_TUTORIAL_KEY).valueRight
        if (showTutorial) {
            val previouslyVisited =
                readBooleanInteractor.run(PREVIOUS_VISIT_CREATE_CARD_KEY).valueRight
            if (!previouslyVisited) {
                withContext(Dispatchers.Main) {
                    view?.showTutorial()
                }
                writeBooleanInteractor.run(
                    WriteBooleanInteractor.Params(PREVIOUS_VISIT_CREATE_CARD_KEY, true)
                )
            }
        }
    }

    override fun detachView(finishing: Boolean) {
        view?.hideKeyboard()
        super.detachView(finishing)
    }

    override fun onSaveCardClicked(card: Card) {
        if (isCardValid(card)) {
            saveCardInteractor(card) {
                it.either(::onSaveCardFailure, ::onSaveCardSuccess)
            }
        }
    }

    private fun isCardValid(card: Card): Boolean {
        val titleEmpty = card.title.isEmpty().also {
            view?.setEmptyTitleErrorVisibility(it)
        }
        val descriptionEmpty = card.description.isEmpty().also {
            view?.setEmptyDescriptionErrorVisibility(it)
        }
        return !titleEmpty && !descriptionEmpty
    }

    private fun onSaveCardFailure(f: Failure) {
        Timber.e("onSaveCardFailure: $f")
    }

    private fun onSaveCardSuccess(card: Card) {
        Timber.d("onSaveCardSuccess: $card")
        cardEditorNavigation.closeScreen(card)
    }

    override fun onScanTextClicked() {
        getPhotoFileInteractor(Unit).either(
            ::onGetPhotoFileFailure,
            ::onGetPhotoFileSuccess
        )
    }

    private fun onGetPhotoFileFailure(failure: Failure) {
        Timber.e("onGetPhotoFileFailure: $failure")
    }

    private fun onGetPhotoFileSuccess(photoFile: File) {
        cachedPhotoFile = photoFile
        cardEditorNavigation.showTakePhotoScreen(
            photoFile,
            CardEditorContract.REQUEST_TAKE_PHOTO
        )
    }

    override fun onTakePhotoResult() {
        cachedPhotoFile?.let {
            view?.showCropPhotoScreen(it)
        } ?: Timber.e("onTakePhotoResult: cached photo is null!")
    }

    override fun recognizeText(photoUri: Uri) {
        progressInteractorExecutor.executeWithProgress(
            R.string.processing_image,
            recognizeTextInteractor,
            photoUri
        ) { it.either(::onRecognizeTextFailure, ::onRecognizeTextSuccess) }
    }

    private fun onRecognizeTextFailure(failure: Failure) {
        Timber.e("onRecognizeTextFailure: $failure")
    }

    private fun onRecognizeTextSuccess(recognizedText: String) {
        view?.showCardDescription(recognizedText)
    }

    companion object {
        private const val PREVIOUS_VISIT_CREATE_CARD_KEY = "PREVIOUS_VISIT_CREATE_CARD"
        private const val SHOW_TUTORIAL_KEY = "SHOW_TUTORIAL"
    }
}
