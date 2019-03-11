package com.cardemory.exampleapp

import com.cardemory.exampleapp.di.component.DaggerAppComponent
import dagger.android.support.DaggerApplication

class ExampleApplication : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder().create(this)!!
}