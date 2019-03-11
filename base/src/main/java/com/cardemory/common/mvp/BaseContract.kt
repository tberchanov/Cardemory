package com.cardemory.common.mvp

import android.os.Bundle
import androidx.annotation.StringRes

import com.cardemory.common.domain.Failure

interface BaseContract {

    interface View {

        fun showError(error: Throwable)

        fun onError(@StringRes messageId: Int)
    }

    interface Presenter<V : View> {

        val view: V?

        fun onError(error: Throwable)

        fun handleFailure(failure: Failure)

        fun attachView(view: V)

        fun detachView(finishing: Boolean = true)

        fun saveViewState(outState: Bundle) {}

        fun restoreViewState(inState: Bundle) {}
    }

}