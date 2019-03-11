package com.cardemory.exampleapp.di.module

import com.cardemory.exampleapp.ExampleApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: ExampleApplication) = application.applicationContext
}