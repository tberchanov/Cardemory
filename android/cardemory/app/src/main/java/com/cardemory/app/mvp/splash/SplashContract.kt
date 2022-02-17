package com.cardemory.app.mvp.splash

import com.cardemory.common.mvp.BaseContract

interface SplashContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View>
}