package com.cardemory.app.util.logging

import android.util.Log
import com.crashlytics.android.Crashlytics

class CrashlyticsTimberTree : DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Crashlytics.log(priority, tag, message)
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }
}