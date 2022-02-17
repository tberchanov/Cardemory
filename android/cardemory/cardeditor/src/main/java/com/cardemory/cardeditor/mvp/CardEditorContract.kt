package com.cardemory.cardeditor.mvp

import android.net.Uri
import com.cardemory.carddata.entity.Card
import com.cardemory.cardeditor.ui.tutorial.CardEditorTutorialSpotlight
import com.cardemory.common.mvp.BaseContract
import java.io.File

interface CardEditorContract {

    interface View : BaseContract.View {

        fun hideKeyboard()

        fun setEmptyTitleErrorVisibility(visible: Boolean)

        fun setEmptyDescriptionErrorVisibility(visible: Boolean)

        fun showCropPhotoScreen(photoFile: File)

        fun showCardDescription(cardDescription: String)

        fun showTutorial()
    }

    interface Presenter : BaseContract.Presenter<View> {

        val cardEditorTutorialSpotlight: CardEditorTutorialSpotlight

        fun onSaveCardClicked(card: Card)

        fun onScanTextClicked()

        fun onTakePhotoResult()

        fun recognizeText(photoUri: Uri)
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
    }
}