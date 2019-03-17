package com.cardemory.cardeditor

import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseContract

interface CardEditorContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View> {

        fun onSaveCardClicked(card: Card)
    }
}