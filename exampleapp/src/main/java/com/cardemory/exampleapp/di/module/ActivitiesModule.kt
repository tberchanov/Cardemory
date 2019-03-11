package com.cardemory.exampleapp.di.module

import com.cardemory.exampleapp.MainActivity
import com.cardemory.exampleapp.di.module.activity.MainActivityFragmentProvider
import com.cardemory.exampleapp.di.module.activity.MainActivityModule
import com.cardemory.exampleapp.di.scope.ActivityScope
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