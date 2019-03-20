package com.cardemory.app.mvp.main

import com.cardemory.app.mvp.main.navigation.MainNavigation
import com.cardemory.common.mvp.BasePresenter
import timber.log.Timber

class MainPresenter(
    private val mainNavigation: MainNavigation
) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    override fun onNavigateButtonClicked() {
        Timber.d("onNavigateButtonClicked")
        mainNavigation.back()
    }

    override fun showRootScreen() {
        mainNavigation.showCardSetList()
    }
}