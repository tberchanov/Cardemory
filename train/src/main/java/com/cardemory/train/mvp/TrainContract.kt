package com.cardemory.train.mvp

import com.cardemory.common.mvp.BaseContract

interface TrainContract {

    interface View : BaseContract.View {

    }

    interface Presenter : BaseContract.Presenter<View> {

    }
}