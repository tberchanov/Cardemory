package com.cardemory.app.di.component

import com.cardemory.app.Application
import com.cardemory.app.di.module.ActivitiesModule
import com.cardemory.app.di.module.AppModule
import com.cardemory.app.di.module.DataModule
import com.cardemory.carddata.di.CardDataModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivitiesModule::class,
        CardDataModule::class,
        DataModule::class
    ]
)
interface AppComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
