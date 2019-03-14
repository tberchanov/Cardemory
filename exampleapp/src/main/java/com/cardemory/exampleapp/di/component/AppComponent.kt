package com.cardemory.exampleapp.di.component

import com.cardemory.exampleapp.ExampleApplication
import com.cardemory.exampleapp.di.module.ActivitiesModule
import com.cardemory.exampleapp.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent : AndroidInjector<ExampleApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ExampleApplication>()
}