package com.cardemory.app

import com.cardemory.app.di.component.DaggerAppComponent
import dagger.android.support.DaggerApplication

class Application : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder().create(this)!!
}