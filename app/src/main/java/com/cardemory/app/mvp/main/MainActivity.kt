package com.cardemory.app.mvp.main

import android.os.Bundle
import com.cardemory.app.R
import com.cardemory.common.mvp.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    BaseActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    override val layoutResId = R.layout.activity_main

    override val fragmentContainerId = R.id.fragmentContainer

    override fun getToolbar() = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            presenter.showRootScreen()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        presenter.onNavigateButtonClicked()
        return true
    }
}
