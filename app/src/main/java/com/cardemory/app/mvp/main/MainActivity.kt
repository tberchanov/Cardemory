package com.cardemory.app.mvp.main

import com.cardemory.app.R
import com.cardemory.common.mvp.BaseActivity

class MainActivity :
    BaseActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    override val layoutResId = R.layout.activity_main
}
