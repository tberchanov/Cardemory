package com.cardemory.exampleapp.di.module

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
}