package com.cardemory.common.mvp

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.cardemory.common.navigation.BaseNavigator
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.NavigatorHolder
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<V : BaseContract.View, P : BaseContract.Presenter<V>> :
    DaggerAppCompatActivity(),
    BaseContract.View {

    @Inject
    lateinit var presenter: P

    @Inject
    lateinit var navigator: BaseNavigator

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected abstract val layoutResId: Int
        @LayoutRes
        get

    @IdRes
    protected open val fragmentContainerId: Int = NO_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLayout()

        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    private fun showLayout() {
        layoutResId.takeIf { it != NO_LAYOUT }
            ?.run { setContentView(this) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.detachView(isFinishing)
        super.onDestroy()
    }

    override fun showError(error: Throwable) {
        Timber.e(error)
    }

    override fun onError(messageId: Int) {
        //TODO not implemented
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveViewState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.restoreViewState(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        navigator.topFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (!onTopFragmentBackPressed()) {
            super.onBackPressed()
        }
    }

    private fun onTopFragmentBackPressed(): Boolean {
        val fragment = getTopFragment(fragmentContainerId)
        return fragment is OnBackPressedListener && fragment.onBackPressed()
    }

    protected fun getTopFragment(@IdRes containerId: Int) =
        supportFragmentManager.findFragmentById(containerId)

    companion object {
        const val NO_LAYOUT = 0
        const val NO_ID = 0
    }
}