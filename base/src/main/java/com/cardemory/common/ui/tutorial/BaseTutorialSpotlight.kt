package com.cardemory.common.ui.tutorial

import android.app.Activity
import android.view.animation.DecelerateInterpolator
import com.cardemory.common.R
import com.takusemba.spotlight.Spotlight

abstract class BaseTutorialSpotlight(private val activity: Activity) {

    private var spotlight: Spotlight? = null

    fun createBaseSpotlight(): Spotlight =
        Spotlight.with(activity)
            .setAnimation(DecelerateInterpolator())
            .setOverlayColor(R.color.black_a6).also {
                spotlight = it
            }

    fun closeSpotlight() = spotlight?.closeSpotlight()
}
