package com.cardemory.app.mvp

import com.cardemory.common.mvp.BaseContract

interface MainContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View> {

        fun onNavigateButtonClicked()

        fun showRootScreen()
    }
}