package com.cardemory.app.di.module

import com.cardemory.app.mvp.main.navigation.*
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
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

    @Provides
    @ActivityScope
    fun provideCardEditorNavigation(router: Router): CardEditorNavigation =
        CardEditorNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardSetListNavigation(router: Router): CardSetListNavigation =
        CardSetListNavigationImpl(router)
}