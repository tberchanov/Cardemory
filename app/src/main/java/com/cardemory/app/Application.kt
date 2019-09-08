package com.cardemory.app

import com.cardemory.app.di.component.DaggerAppComponent
import com.cardemory.app.util.logging.CrashlyticsTimberTree
import com.cardemory.app.util.logging.DebugTree
import com.cardemory.app.util.logging.FileTimberTree
import dagger.android.support.DaggerApplication
import timber.log.Timber

class Application : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder().create(this)!!

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree(), FileTimberTree(this))
        } else {
            Timber.plant(CrashlyticsTimberTree())
        }
    }
}