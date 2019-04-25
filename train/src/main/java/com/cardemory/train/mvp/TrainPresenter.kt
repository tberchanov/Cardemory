package com.cardemory.train.mvp

import com.cardemory.common.mvp.BasePresenter
import com.cardemory.train.navigation.TrainNavigation

class TrainPresenter(
    private val trainNavigation: TrainNavigation
) : BasePresenter<TrainContract.View>(),
    TrainContract.Presenter {
}