package com.cardemory.card_set_editor.mvp

import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseContract

interface CardSetEditorContract {

    interface View : BaseContract.View {

        fun hideKeyboard()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun onSaveCardSetClicked(cardSet: CardSet)
    }
}