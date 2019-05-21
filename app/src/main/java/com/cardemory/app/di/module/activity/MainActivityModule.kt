package com.cardemory.app.di.module.activity

import com.cardemory.app.mvp.main.MainActivity
import com.cardemory.app.mvp.main.MainContract
import com.cardemory.app.mvp.main.MainPresenter
import com.cardemory.app.navigation.MainNavigation
import com.cardemory.app.navigation.MainNavigator
import com.cardemory.common.di.scope.ActivityScope
import com.cardemory.common.interactor.RequestPermissionsInteractor
import com.cardemory.common.navigation.BaseNavigator
import com.cardemory.common.util.ProgressInteractorExecutor
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ObsoleteCoroutinesApi

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(activity: MainActivity): BaseNavigator =
        MainNavigator(activity)

    @Provides
    @ActivityScope
    fun providePresenter(mainNavigation: MainNavigation): MainContract.Presenter =
        MainPresenter(mainNavigation)

    @ObsoleteCoroutinesApi
    @Provides
    @ActivityScope
    fun provideRequestPermissionsInteractor(activity: MainActivity) =
        RequestPermissionsInteractor(activity)

    @Provides
    @ActivityScope
    fun provideProgressInteractorExecutor(activity: MainActivity) =
        ProgressInteractorExecutor(activity)
}