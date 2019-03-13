package com.cardemory.app.di.module

import com.cardemory.app.mvp.main.navigation.CardListNavigationImpl
import com.cardemory.app.mvp.main.navigation.MainNavigation
import com.cardemory.app.mvp.main.navigation.MainNavigationImpl
import com.cardemory.cardlist.CardListNavigation
import com.cardemory.common.di.scope.ActivityScope
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class NavigationModule {

    private val cicerone = Cicerone.create(Router())

    @Provides
    @ActivityScope
    fun provideRouter() = cicerone.router!!

    @Provides
    @ActivityScope
    fun provideNavigatorHolder() = cicerone.navigatorHolder!!

    @Provides
    @ActivityScope
    fun provideMainNavigation(router: Router): MainNavigation =
        MainNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardListNavigation(router: Router): CardListNavigation =
        CardListNavigationImpl(router)
}