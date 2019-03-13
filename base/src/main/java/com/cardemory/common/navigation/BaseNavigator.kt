package com.cardemory.common.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

abstract class BaseNavigator(
    private val activity: FragmentActivity,
    @param:IdRes @field:IdRes private val containerId: Int
) : SupportAppNavigator(activity, containerId) {

    val topFragment: Fragment?
        get() = activity.supportFragmentManager
            .findFragmentById(containerId)

    override fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        fragmentTransaction: FragmentTransaction
    ) {
        fragmentTransaction.setCustomAnimations(
            android.R.anim.fade_in, android.R.anim.fade_out,
            android.R.anim.fade_in, android.R.anim.fade_out
        )
    }
}