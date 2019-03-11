package com.cardemory.exampleapp

import androidx.fragment.app.FragmentActivity
import com.cardemory.common.navigation.BaseNavigator

class ExampleNavigator(
    activity: FragmentActivity
) : BaseNavigator(activity, R.id.fragmentContainer)