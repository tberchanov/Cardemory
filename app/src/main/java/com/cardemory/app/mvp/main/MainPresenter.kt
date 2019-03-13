package com.cardemory.app.mvp.main

import com.cardemory.app.mvp.main.navigation.MainNavigation
import com.cardemory.common.mvp.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainPresenter(
    private val mainNavigation: MainNavigation
) : BasePresenter<MainContract.View>(),
    MainContract.Presenter {

    override fun attachView(view: MainContract.View) {
        super.attachView(view)

        launch(Dispatchers.Main) {
            delay(3000)
            mainNavigation.showCardList()
        }
    }
}