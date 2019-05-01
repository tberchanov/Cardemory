package com.cardemory.common.mvp

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : Fragment(),
    BaseContract.View {

    @Inject
    lateinit var presenter: P

    protected abstract val layoutResId: Int
        @LayoutRes
        get

    protected open val titleRes: Int = NO_TITLE
        @StringRes
        get

    protected open val title: String
        get() = titleRes.takeIf { it != NO_TITLE }?.let {
            getString(it)
        } ?: ""

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        presenter.attachView(this as V)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch(Dispatchers.Main) {
            getToolbar()?.title = title
        }
    }

    override fun onDestroyView() {
        presenter.detachView(activity?.isFinishing ?: true)
        super.onDestroyView()
    }

    override fun showError(error: Throwable) {
        Timber.e(error)
    }

    override fun onError(messageId: Int) {
        //TODO not implemented
    }

    protected fun isPresenterInitialized() = ::presenter.isInitialized

    fun requireArguments(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments not found")
    }

    protected fun getToolbar(): Toolbar? {
        return getBaseActivity().getToolbar()
    }

    protected fun setBackButtonVisibility(visible: Boolean) {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(visible)
            setDisplayShowHomeEnabled(visible)
        }
    }

    protected fun writeToActivityStorage(key: String, value: Parcelable) {
        getBaseActivity().writeToStorage(key, value)
    }

    protected fun <T : Parcelable> readFromActivityStorage(key: String) =
        getBaseActivity().readFromStorage<T>(key)

    protected fun removeFromActivityStorage(key: String) =
        getBaseActivity().removeFromStorage(key)

    private fun getBaseActivity() = activity as BaseActivity<*, *>

    companion object {

        private const val NO_TITLE = 0
    }
}