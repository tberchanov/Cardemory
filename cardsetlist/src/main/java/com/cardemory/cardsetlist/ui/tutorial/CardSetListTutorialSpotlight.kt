package com.cardemory.cardsetlist.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialSpotlight
import com.takusemba.spotlight.Spotlight
import javax.inject.Inject

class CardSetListTutorialSpotlight @Inject constructor(
    activity: Activity,
    private val createCardSetButtonTarget: CreateCardSetButtonTarget
) : BaseTutorialSpotlight(activity) {

    fun createSpotlight(
        createCardSetButton: View
    ): Spotlight = createBaseSpotlight().setTargets(
        createCardSetButtonTarget.create(createCardSetButton)
    )
}