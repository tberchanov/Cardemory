package com.cardemory.train.mvp

import com.cardemory.common.mvp.BaseFragment
import com.cardemory.train.R

class TrainFragment :
    BaseFragment<TrainContract.View, TrainContract.Presenter>(),
    TrainContract.View {

    override val layoutResId = R.layout.fragment_train

    companion object {

        fun newInstance() = TrainFragment()
    }
}