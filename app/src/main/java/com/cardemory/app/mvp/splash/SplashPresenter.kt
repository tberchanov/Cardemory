package com.cardemory.app.mvp.splash

import com.cardemory.app.navigation.SplashNavigation
import com.cardemory.common.mvp.BasePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashPresenter(
    private val splashNavigation: SplashNavigation
) : BasePresenter<SplashContract.View>(),
    SplashContract.Presenter {

    override fun attachView(view: SplashContract.View) {
        super.attachView(view)
        launch(Dispatchers.Main) {
            delay(SPLASH_DELAY)
            splashNavigation.showMainScreen()
        }
    }

    companion object {
        private const val SPLASH_DELAY = 500L
    }
}