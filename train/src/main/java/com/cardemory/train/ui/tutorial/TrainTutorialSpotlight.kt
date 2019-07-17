package com.cardemory.train.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialSpotlight
import javax.inject.Inject

class TrainTutorialSpotlight
@Inject constructor(
    activity: Activity,
    private val forgotButtonTarget: ForgotButtonTarget,
    private val rememberedButtonTarget: RememberedButtonTarget,
    private val trainCardTarget: TrainCardTarget
) : BaseTutorialSpotlight(activity) {

    fun createSpotlight(
        forgotButton: View,
        rememberedButton: View,
        trainCard: View
    ) = createBaseSpotlight().setTargets(
        forgotButtonTarget.create(forgotButton),
        rememberedButtonTarget.create(rememberedButton),
        trainCardTarget.create(trainCard)
    )
}