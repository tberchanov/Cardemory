package com.cardemory.app.di.module

import android.content.Context
import com.cardemory.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application) = application.applicationContext!!

    @Provides
    @Singleton
    fun provideContentResolver(context: Context) = context.contentResolver
}