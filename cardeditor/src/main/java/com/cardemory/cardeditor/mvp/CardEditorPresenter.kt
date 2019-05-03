package com.cardemory.cardeditor.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.cardeditor.interactor.GetPhotoFileInteractor
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.common.mvp.BasePresenter
import com.cardemory.infrastructure.entity.Failure
import timber.log.Timber
import java.io.File

class CardEditorPresenter(
    private val cardEditorNavigation: CardEditorNavigation,
    private val saveCardInteractor: SaveCardInteractor,
    private val getPhotoFileInteractor: GetPhotoFileInteractor
) : BasePresenter<CardEditorContract.View>(),
    CardEditorContract.Presenter {

    private var cachedPhotoFile: File? = null

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
}
