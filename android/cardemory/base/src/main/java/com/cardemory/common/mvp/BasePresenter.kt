package com.cardemory.common.mvp

import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class BasePresenter<V : BaseContract.View> :
    BaseContract.Presenter<V>,
    CoroutineScope {

    override var coroutineContext: CoroutineContext = Dispatchers.Main

    override var view: V? = null

    override fun onError(error: Throwable) {
        Timber.e(error)
        view?.showError(error)
    }

    override fun handleFailure(failure: Failure) {
        Timber.e("Error $failure")
    }

    override fun attachView(view: V) {
        this.view = view
        this.coroutineContext += Job()
    }

    override fun detachView(finishing: Boolean) {
        this.view = null
        coroutineContext.cancel()
    }
}