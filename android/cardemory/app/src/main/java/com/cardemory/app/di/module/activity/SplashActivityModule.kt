package com.cardemory.app.di.module.activity

import com.cardemory.app.mvp.splash.SplashActivity
import com.cardemory.app.mvp.splash.SplashContract
import com.cardemory.app.mvp.splash.SplashPresenter
import com.cardemory.app.navigation.SplashNavigation
import com.cardemory.app.navigation.SplashNavigator
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.interactor.GetAllCardsInteractor
import com.cardemory.carddata.interactor.PrepopulateDbInteractor
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
    fun providePresenter(
        navigation: SplashNavigation,
        prepopulateDbInteractor: PrepopulateDbInteractor,
        getAllCardsInteractor: GetAllCardsInteractor,
        appDatabase: AppDatabase
    ): SplashContract.Presenter =
        SplashPresenter(
            navigation,
            prepopulateDbInteractor,
            getAllCardsInteractor,
            appDatabase
        )
}