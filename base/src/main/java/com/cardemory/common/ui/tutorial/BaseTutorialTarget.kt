package com.cardemory.common.ui.tutorial

import android.view.View
import com.takusemba.spotlight.target.Target

abstract class BaseTutorialTarget {

    abstract fun create(tutorialView: View): Target
}
