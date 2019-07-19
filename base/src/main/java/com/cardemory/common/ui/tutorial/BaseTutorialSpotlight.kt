package com.cardemory.common.ui.tutorial

import android.app.Activity
import android.view.animation.DecelerateInterpolator
import com.cardemory.common.R
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.Spotlight

abstract class BaseTutorialSpotlight(private val activity: Activity) {

    var spotlightVisible = false
        private set

    private var spotlight: Spotlight? = null

    fun createBaseSpotlight(): Spotlight =
        Spotlight.with(activity)
            .setAnimation(DecelerateInterpolator())
            .setOverlayColor(R.color.black_a6)
            .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
                override fun onStarted() {
                    spotlightVisible = true
                }

                override fun onEnded() {
                    spotlightVisible = false
                }

            })
            .also { spotlight = it }

    fun closeSpotlight() = spotlight?.closeSpotlight()
}
