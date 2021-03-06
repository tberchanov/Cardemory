package com.cardemory.common.mvp

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
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

    protected open val titleRes: Int = NO_TITLE
        @StringRes
        get

    protected open val title: String
        get() = titleRes.takeIf { it != NO_TITLE }?.let {
            getString(it)
        } ?: ""

    private val storage = mutableMapOf<String, Parcelable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.also(::restoreStorage)
        showLayout()

        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    private fun restoreStorage(savedInstanceState: Bundle) {
        val keysArray = savedInstanceState.getStringArray(KEYS_ARRAY_KEY)!!
        for (key in keysArray) {
            savedInstanceState.getParcelable<Parcelable>(key)?.also {
                writeToStorage(key, it)
            }
        }
    }

    private fun showLayout() {
        layoutResId.takeIf { it != NO_LAYOUT }
            ?.run { setContentView(this) }
    }

    override fun onStart() {
        super.onStart()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        getToolbar()?.also {
            setSupportActionBar(it)
            it.title = title
        }
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

        val storageKeys = storage.keys.toTypedArray()
        outState.putStringArray(KEYS_ARRAY_KEY, storageKeys)
        for (key in storageKeys) {
            outState.putParcelable(key, readFromStorage(key))
        }
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

    abstract fun getToolbar(): Toolbar?

    fun writeToStorage(key: String, value: Parcelable) {
        storage[key] = value
    }

    fun <T : Parcelable> readFromStorage(key: String): T? {
        @Suppress("UNCHECKED_CAST")
        return (storage[key] as? T).also {
            storage.remove(key)
        }
    }

    fun removeFromStorage(key: String) = storage.remove(key) != null

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackButtonClicked()
            }
            else -> super.onOptionsItemSelected(item)
        }

    open fun onBackButtonClicked() = onTopFragmentBackPressed()

    companion object {
        const val KEYS_ARRAY_KEY = "KEYS_ARRAY_KEY"

        const val NO_LAYOUT = 0
        const val NO_ID = 0
        const val NO_TITLE = 0
    }
}