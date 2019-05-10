package com.cardemory.app.di.module.activity

import com.cardemory.app.mvp.splash.SplashActivity
import com.cardemory.app.mvp.splash.SplashContract
import com.cardemory.app.mvp.splash.SplashPresenter
import com.cardemory.app.navigation.SplashNavigation
import com.cardemory.app.navigation.SplashNavigator
import com.cardemory.common.di.scope.ActivityScope
import com.cardemory.common.navigation.BaseNavigator
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(activity: SplashActivity): BaseNavigator =
        SplashNavigator(activity)

    @Provides
    @ActivityScope
    fun providePresenter(navigation: SplashNavigation): SplashContract.Presenter =
        SplashPresenter(navigation)
}