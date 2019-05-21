package com.cardemory.common.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.cardemory.common.R
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import kotlinx.android.synthetic.main.dialog_progress.view.*

class ProgressInteractorExecutor(private val activity: Activity) {

    @SuppressLint("InflateParams")
    private val dialogView = LayoutInflater.from(activity)
        .inflate(R.layout.dialog_progress, null)

    private val dialog = AlertDialog.Builder(activity)
        .setView(dialogView)
        .setCancelable(false)
        .create()

    init {
        val progressColor = ContextCompat.getColor(activity, R.color.colorProgress)
        dialogView.progressBar.indeterminateTintList = ColorStateList.valueOf(progressColor)
    }

    fun <Type : Any, Params> executeWithProgress(
        @StringRes progressMessageRes: Int,
        interactor: BaseSingleInteractor<Type, Params>,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) = executeWithProgress(
        activity.getString(progressMessageRes),
        interactor,
        params,
        onResult
    )

    fun <Type : Any, Params> executeWithProgress(
        progressMessage: String,
        interactor: BaseSingleInteractor<Type, Params>,
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {}
    ) {
        dialogView.cancelButton.setOnClickListener {
            interactor.cancel()
            dialog.dismiss()
        }
        dialogView.progressMessageTextView.text = progressMessage
        dialog.show()
        interactor(params) {
            dialog.dismiss()
            onResult(it)
        }
    }
}
