package com.cardemory.app.di.module.activity

import com.cardemory.app.mvp.main.MainActivity
import com.cardemory.app.mvp.main.MainContract
import com.cardemory.app.mvp.main.MainPresenter
import com.cardemory.app.mvp.main.navigation.MainNavigation
import com.cardemory.app.mvp.main.navigation.MainNavigationImpl
import com.cardemory.app.mvp.main.navigation.MainNavigator
import com.cardemory.common.di.scope.ActivityScope
import com.cardemory.common.navigation.BaseNavigator
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

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

    @Provides
    @ActivityScope
    fun provideNavigation(router: Router): MainNavigation =
        MainNavigationImpl(router)
}