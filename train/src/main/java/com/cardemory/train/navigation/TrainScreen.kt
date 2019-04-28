package com.cardemory.train.navigation

import com.cardemory.carddata.entity.CardSet
import com.cardemory.train.mvp.TrainFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TrainScreen(private val cardSet: CardSet) : SupportAppScreen() {

    override fun getFragment() = TrainFragment.newInstance(cardSet)
}