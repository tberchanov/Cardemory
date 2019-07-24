package com.cardemory.cardeditor.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialSpotlight
import com.takusemba.spotlight.Spotlight
import javax.inject.Inject

class CardEditorTutorialSpotlight
@Inject constructor(
    activity: Activity,
    private val ocrButtonTarget: OcrButtonTarget
) : BaseTutorialSpotlight(activity) {

    fun createSpotlight(
        ocrButton: View
    ): Spotlight = createBaseSpotlight().setTargets(
        ocrButtonTarget.create(ocrButton)
    )
}