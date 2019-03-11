package com.cardemory.exampleapp

import com.cardemory.common.mvp.BaseContract

interface MainContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View>
}