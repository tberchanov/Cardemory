package com.cardemory.app.navigation

import android.content.Context
import android.content.Intent
import com.cardemory.app.mvp.main.MainActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainScreen : SupportAppScreen() {

    override fun getActivityIntent(context: Context?): Intent {
        return Intent(context, MainActivity::class.java)
    }
}