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
        val locationOnScreenArr = IntArray(LOCATION_DIMENSIONS_COUNT)
        tutorialView.getLocationOnScreen(locationOnScreenArr)

        val hintOverlay = PointF(
            locationOnScreenArr[HORIZONTAL_LOCATION_INDEX].toFloat(),
            locationOnScreenArr[VERTICAL_LOCATION_INDEX]
                + tutorialView.height
                + activity.getDimen(R.dimen.train_card_hint_overlay_top)
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

    companion object {
        private const val LOCATION_DIMENSIONS_COUNT = 2

        private const val VERTICAL_LOCATION_INDEX = 1

        private const val HORIZONTAL_LOCATION_INDEX = 0
    }
}