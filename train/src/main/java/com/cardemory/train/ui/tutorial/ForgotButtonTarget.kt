package com.cardemory.train.ui.tutorial

import android.app.Activity
import android.view.View
import com.cardemory.common.ui.tutorial.BaseTutorialTarget
import com.cardemory.common.util.ext.getDimen
import com.cardemory.train.R
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.CustomTarget
import com.takusemba.spotlight.target.Target
import javax.inject.Inject

class ForgotButtonTarget
@Inject constructor(
    private val activity: Activity
) : BaseTutorialTarget() {

    override fun create(tutorialView: View): Target {
        val locationOnScreenArr = IntArray(LOCATION_DIMENSIONS_COUNT)
        tutorialView.getLocationOnScreen(locationOnScreenArr)

        /*  val hintOverlay = PointF(
              activity.getDimen(R.dimen.forget_hint_overlay_left),
              locationOnScreenArr[VERTICAL_LOCATION_INDEX]
                  - tutorialView.height
                  - activity.getDimen(R.dimen.train_button_description_top_margin)
          )*/
        val hintShape = RoundedRectangle(
            tutorialView.height.toFloat(),
            tutorialView.width + activity.getDimen(R.dimen.forget_reminded_hint_horizontal_padding),
            activity.getDimen(R.dimen.rectangle_hint_shape_radius)
        )
        return CustomTarget.Builder(activity)
            .setPoint(tutorialView)
            .setShape(hintShape)
            .setOverlay(R.layout.view_train_swipe_tutorial)
            //            .setDescription(activity.getString(R.string.forgot_hint_description))
            //            .setOverlayPoint(hintOverlay)
            .build()
    }

    companion object {
        private const val LOCATION_DIMENSIONS_COUNT = 2

        private const val VERTICAL_LOCATION_INDEX = 1
    }
}