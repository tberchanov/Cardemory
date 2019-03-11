package com.cardemory.common.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<V : BaseContract.View, P : BaseContract.Presenter<V>> : Fragment(),
    BaseContract.View {

    @Inject
    lateinit var presenter: P

    protected abstract val layoutResId: Int
        @LayoutRes
        get

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

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
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

    fun setTitle(@StringRes titleRes: Int) {
        (activity as? AppCompatActivity)?.supportActionBar?.setTitle(titleRes)
            ?: Timber.e("SupportActionBar not found!")
    }

    protected fun isPresenterInitialized() = ::presenter.isInitialized

    fun requireArguments(): Bundle {
        return arguments ?: throw IllegalStateException("Arguments not found")
    }
}