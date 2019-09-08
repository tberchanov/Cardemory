package com.cardemory.train.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.cardemory.train.R
import com.cardemory.train.ui.widget.StarState
import kotlinx.android.synthetic.main.dialog_finish_train.view.*

class FinishTrainDialog(
    @StringRes private val titleRes: Int,
    @StringRes private val messageRes: Int,
    @ColorRes private val textColorRes: Int,
    private val onBackClicked: () -> Unit,
    private vararg val starState: StarState
) : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_finish_train, null)

        val textColor = ContextCompat.getColor(context!!, textColorRes)
        view.trainResultTitleTextView.setTextColor(textColor)
        view.trainResultDialogMessage.setTextColor(textColor)
        view.backButton.also {
            it.setTextColor(textColor)
            it.setRippleColorResource(textColorRes)
        }

        view.trainResultTitleTextView.setText(titleRes)
        view.trainResultDialogMessage.setText(messageRes)
        view.backButton.setOnClickListener {
            onBackClicked()
            dismiss()
        }
        starState.forEachIndexed { index, starState ->
            view.starView.setStarState(index, starState)
        }
        return AlertDialog.Builder(activity!!)
            .setView(view)
            .create()
    }
}
