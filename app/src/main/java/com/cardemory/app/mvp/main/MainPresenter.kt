package com.cardemory.app.mvp.main

import com.cardemory.app.mvp.main.navigation.MainNavigation
import com.cardemory.common.mvp.BasePresenter

class MainPresenter(
    private val mainNavigation: MainNavigation
) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    override fun attachView(view: MainContract.View) {
        super.attachView(view)

        mainNavigation.showCardList()
    }
}