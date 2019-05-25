package com.cardemory.card_set_editor.mvp

import android.net.Uri
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseContract

interface CardSetEditorContract {

    interface View : BaseContract.View {

        fun hideKeyboard()

        fun showInvalidFileContentsMessage()

        fun showCardSet(cardSet: CardSet)
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onSaveCardSetClicked(cardSet: CardSet)

        fun selectFile()

        fun onImportFileSelected(fileUri: Uri)
    }

    companion object {
        const val SELECT_FILE_REQUEST_CODE = 1
    }
}