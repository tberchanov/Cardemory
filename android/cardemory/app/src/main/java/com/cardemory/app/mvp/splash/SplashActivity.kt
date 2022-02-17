package com.cardemory.app.mvp.splash

import com.cardemory.common.mvp.BaseActivity

class SplashActivity :
    BaseActivity<SplashContract.View, SplashContract.Presenter>(),
    SplashContract.View {

    override fun getToolbar() = null

    override val layoutResId = NO_LAYOUT
}