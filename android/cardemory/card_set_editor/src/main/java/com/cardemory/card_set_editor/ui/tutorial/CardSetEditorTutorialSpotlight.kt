package com.cardemory.card_set_editor.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialSpotlight
import com.takusemba.spotlight.Spotlight
import javax.inject.Inject

class CardSetEditorTutorialSpotlight
@Inject constructor(
    activity: Activity,
    private val importButtonTarget: ImportButtonTarget
) : BaseTutorialSpotlight(activity) {

    fun createSpotlight(
        importButton: View
    ): Spotlight = createBaseSpotlight().setTargets(
        importButtonTarget.create(importButton)
    )
}