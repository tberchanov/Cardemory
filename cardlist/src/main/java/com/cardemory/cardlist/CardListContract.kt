package com.cardemory.cardlist

import com.cardemory.common.mvp.BaseContract

interface CardListContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View>
}