package com.cardemory.cardlist.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialSpotlight
import com.takusemba.spotlight.Spotlight
import javax.inject.Inject


class CardListTutorialSpotlight
@Inject constructor(
    activity: Activity,
    private val createCardButtonTarget: CreateCardButtonTarget,
    private val exportButtonTarget: ExportButtonTarget,
    private val trainingButtonTarget: TrainingButtonTarget
) : BaseTutorialSpotlight(activity) {

    fun createSpotlight(
        createCardButton: View,
        exportButton: View,
        trainButton: View
    ): Spotlight = createBaseSpotlight().setTargets(
        createCardButtonTarget.create(createCardButton),
        exportButtonTarget.create(exportButton),
        trainingButtonTarget.create(trainButton)
    )
}