package com.cardemory.cardsetlist.mvp

import com.cardemory.common.mvp.BaseContract

interface CardSetListContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View> {

        fun onCreateCardSetClicked()
    }
}