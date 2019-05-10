package com.cardemory.app.navigation

import com.cardemory.common.navigation.AppRouter

class SplashNavigationImpl(
    private val router: AppRouter
) : SplashNavigation {

    override fun showMainScreen() {
        router.newRootScreen(MainScreen())
    }
}