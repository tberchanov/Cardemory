package com.cardemory.app.di.module

import com.cardemory.app.di.module.activity.MainActivityFragmentProvider
import com.cardemory.app.di.module.activity.MainActivityModule
import com.cardemory.app.mvp.main.MainActivity
import com.cardemory.common.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityFragmentProvider::class,
            MainActivityModule::class,
            NavigationModule::class
        ]
    )
    abstract fun contributesMainActivityInjector(): MainActivity
}