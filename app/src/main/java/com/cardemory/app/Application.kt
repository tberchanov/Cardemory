package com.cardemory.app

import com.cardemory.app.di.component.DaggerAppComponent
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
            Timber.plant(Timber.DebugTree(), FileTimberTree(this))
        }
    }
}