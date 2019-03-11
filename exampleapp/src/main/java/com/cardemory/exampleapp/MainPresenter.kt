package com.cardemory.exampleapp

import com.cardemory.common.mvp.BasePresenter
import timber.log.Timber

class MainPresenter() : BasePresenter<MainContract.View>(), MainContract.Presenter {

    override fun attachView(view: MainContract.View) {
        super.attachView(view)
        Timber.e("MainPresenter attach")

    }
}