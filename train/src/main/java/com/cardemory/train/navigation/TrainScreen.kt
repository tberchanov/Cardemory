package com.cardemory.train.navigation

import com.cardemory.train.mvp.TrainFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TrainScreen : SupportAppScreen() {

    override fun getFragment() = TrainFragment.newInstance()
}