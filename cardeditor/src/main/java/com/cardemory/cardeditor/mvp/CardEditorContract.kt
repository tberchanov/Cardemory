package com.cardemory.cardeditor.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract

interface CardEditorContract {

    interface View : BaseContract.View {

        fun hideKeyboard()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onSaveCardClicked(card: Card)

        fun onScanTextClicked()

        fun onTakePhotoResult()
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
    }
}