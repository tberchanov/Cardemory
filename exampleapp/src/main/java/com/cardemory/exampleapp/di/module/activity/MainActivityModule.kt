package com.cardemory.exampleapp.di.module.activity

import android.app.Activity
import com.cardemory.common.navigation.BaseNavigator
import com.cardemory.exampleapp.ExampleNavigator
import com.cardemory.exampleapp.MainActivity
import com.cardemory.exampleapp.MainContract
import com.cardemory.exampleapp.MainPresenter
import com.cardemory.exampleapp.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(activity: MainActivity): BaseNavigator = ExampleNavigator(activity)

    @Provides
    @ActivityScope
    fun providePresenter(): MainContract.Presenter = MainPresenter()

    @Provides
    @ActivityScope
    fun provideActivity(activity: MainActivity): Activity = activity
}