package com.cardemory.app.util.logging

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

class CrashlyticsTimberTree : DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val throwableMessage = t?.let { "Throwable: $it" } ?: ""
        Firebase.crashlytics.log("$tag: $message $throwableMessage")
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.DEBUG
    }
}