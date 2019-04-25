package com.cardemory.app.di.module

import com.cardemory.app.navigation.*
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.di.scope.ActivityScope
import com.cardemory.common.navigation.AppRouter
import com.cardemory.train.navigation.TrainNavigation
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

@Module
class NavigationModule {

    private val cicerone = Cicerone.create(AppRouter())

    @Provides
    @ActivityScope
    fun provideRouter() = cicerone.router!!

    @Provides
    @ActivityScope
    fun provideNavigatorHolder() = cicerone.navigatorHolder!!

    @Provides
    @ActivityScope
    fun provideMainNavigation(router: AppRouter): MainNavigation =
        MainNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardListNavigation(router: AppRouter): CardListNavigation =
        CardListNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardEditorNavigation(router: AppRouter): CardEditorNavigation =
        CardEditorNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardSetListNavigation(router: AppRouter): CardSetListNavigation =
        CardSetListNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideCardSetEditorNavigation(router: AppRouter): CardSetEditorNavigation =
        CardSetEditorNavigationImpl(router)

    @Provides
    @ActivityScope
    fun provideTrainNavigation(): TrainNavigation =
        TrainNavigationImpl()
}
