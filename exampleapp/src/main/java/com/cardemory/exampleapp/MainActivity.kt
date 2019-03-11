package com.cardemory.exampleapp

import com.cardemory.common.mvp.BaseActivity

class MainActivity :
    BaseActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    override val layoutResId = R.layout.activity_main
}
