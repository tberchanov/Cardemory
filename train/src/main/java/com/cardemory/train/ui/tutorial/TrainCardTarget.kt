package com.cardemory.train.ui.tutorial

import android.app.Activity
import android.graphics.PointF
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialTarget
import com.cardemory.common.util.ext.getDimen
import com.cardemory.train.R
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import javax.inject.Inject

class TrainCardTarget @Inject constructor(
    private val activity: Activity
) : BaseTutorialTarget() {

    override fun create(tutorialView: View): Target {
        val hintOverlay = PointF(
            activity.getDimen(R.dimen.train_card_hint_overlay_left),
            activity.getDimen(R.dimen.train_card_hint_overlay_top)
        )
        val trainCardHintPadding = activity.getDimen(R.dimen.train_card_hint_padding)
        val hintShape = RoundedRectangle(
            tutorialView.height + trainCardHintPadding,
            tutorialView.width + trainCardHintPadding,
            activity.getDimen(R.dimen.rectangle_hint_shape_radius)
        )
        return SimpleTarget.Builder(activity)
            .setPoint(tutorialView)
            .setShape(hintShape)
            .setTitle(activity.getString(R.string.train_card_hint_title))
            .setDescription(activity.getString(R.string.train_card_hint_description))
            .setOverlayPoint(hintOverlay)
            .build()
    }
}