package com.cardemory.cardsetlist.mvp.privacy

import com.cardemory.common.mvp.BaseContract

interface PrivacyPolicyContract {

    interface View : BaseContract.View


    interface Presenter : BaseContract.Presenter<View>
}