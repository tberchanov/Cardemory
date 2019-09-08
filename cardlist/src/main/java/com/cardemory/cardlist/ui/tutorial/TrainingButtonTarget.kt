package com.cardemory.cardlist.ui.tutorial

import android.app.Activity
import android.graphics.PointF
import android.view.View
import com.cardemory.cardlist.R
import com.cardemory.common.ui.tutorial.BaseTutorialTarget
import com.cardemory.common.util.ext.getDimen
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import javax.inject.Inject

class TrainingButtonTarget
@Inject constructor(
    private val activity: Activity
) : BaseTutorialTarget() {

    override fun create(tutorialView: View): Target {
        val locationOnScreenArr = IntArray(LOCATION_DIMENSIONS_COUNT)
        tutorialView.getLocationOnScreen(locationOnScreenArr)

        val overlayPoint = PointF(
            locationOnScreenArr[HORIZONTAL_LOCATION_INDEX]
                + activity.getDimen(R.dimen.training_hint_overlay_left),
            locationOnScreenArr[VERTICAL_LOCATION_INDEX]
                + activity.getDimen(R.dimen.training_hint_overlay_top)
        )
        val hintShape = RoundedRectangle(
            tutorialView.height.toFloat(),
            tutorialView.width - activity.getDimen(R.dimen.training_hint_width_paddings),
            activity.getDimen(R.dimen.rectangle_hint_shape_radius)
        )
        return SimpleTarget.Builder(activity)
            .setPoint(tutorialView)
            .setShape(hintShape)
            .setTitle(activity.getString(R.string.start_training))
            .setDescription(activity.getString(R.string.start_training_description))
            .setOverlayPoint(overlayPoint)
            .build()
    }

    companion object {
        private const val LOCATION_DIMENSIONS_COUNT = 2

        private const val VERTICAL_LOCATION_INDEX = 1

        private const val HORIZONTAL_LOCATION_INDEX = 0
    }
}