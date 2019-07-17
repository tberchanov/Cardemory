package com.cardemory.card_set_editor.mvp

import android.app.Activity
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.cardemory.card_set_editor.R
import com.cardemory.card_set_editor.mvp.CardSetEditorContract.Companion.SELECT_FILE_REQUEST_CODE
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.ext.getDimen
import com.cardemory.common.util.ext.setVisible
import com.cardemory.common.util.hideKeyboard
import com.cardemory.common.util.showKeyboard
import com.google.android.material.snackbar.Snackbar
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.SimpleTarget
import com.takusemba.spotlight.target.Target
import kotlinx.android.synthetic.main.fragment_card_set_editor.*
import timber.log.Timber

class CardSetEditorFragment :
    BaseFragment<CardSetEditorContract.View, CardSetEditorContract.Presenter>(),
    CardSetEditorContract.View {

    private var loadedCardSet: CardSet? = null

    override val layoutResId = R.layout.fragment_card_set_editor

    override val titleRes by lazy {
        if (isEditMode())
            R.string.title_edit_edit_card_set
        else
            R.string.title_create_card_set
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveCardSetButton.setOnClickListener {
            presenter.onSaveCardSetClicked(getCardSet())
        }
        importButton.setVisible(!isEditMode())
        importButton.setOnClickListener {
            presenter.selectFile()
        }
        getCardSetArg()?.also {
            cardSetNameEditText.setText(it.name)
        }
        cardSetNameEditText.post {
            cardSetNameEditText.showKeyboard()
        }
    }

    private fun getCardSet() =
        loadedCardSet
            ?.copy(name = getEditedCardSetName())
            ?: CardSet(
                getCardSetArg()?.id ?: CardSet.UNDEFINED_ID,
                getEditedCardSetName(),
                emptyMap()
            )

    private fun getEditedCardSetName() = cardSetNameEditText.text.toString()

    override fun onStart() {
        super.onStart()
        Handler().post {
            setBackButtonVisibility(true)
        }
    }

    override fun onStop() {
        setBackButtonVisibility(false)
        super.onStop()
    }

    override fun hideKeyboard() {
        cardSetNameEditText.hideKeyboard()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) =
        when (requestCode) {
            SELECT_FILE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    onSelectFileResult(data)
                } else {
                    Timber.d("onSelectFile failed: $resultCode")
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }

    private fun onSelectFileResult(data: Intent) {
        data.data?.let {
            presenter.onImportFileSelected(it)
        }
    }

    override fun showInvalidFileContentsMessage() {
        Snackbar.make(
            containerCoordinator,
            R.string.invalid_file_format,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showCardSet(cardSet: CardSet) {
        loadedCardSet = cardSet
        cardSetNameEditText.setText(cardSet.name)
    }

    override fun showEmptyNameError() {
        cardSetNameTIL.error = getString(R.string.empty_name_error)
    }

    override fun showTutorialImport() {
        Spotlight.with(requireActivity())
            .setAnimation(DecelerateInterpolator())
            .setTargets(createTargetImportButton())
            .setOverlayColor(R.color.black_a6)
            .start()
    }

    private fun createTargetImportButton(): Target {
        val createCardSetHintOverlay = PointF(
            importButton.x - getDimen(R.dimen.import_cardset_hint_overlay_left),
            importButton.y
        )
        val hintShape = RoundedRectangle(
            importButton.height.toFloat(),
            importButton.width + getDimen(R.dimen.import_cardset_hint_width_paddings),
            getDimen(R.dimen.rectangle_hint_shape_radius)
        )
        return SimpleTarget.Builder(requireActivity())
            .setPoint(importButton)
            .setShape(hintShape)
            .setTitle(getString(R.string.import_card_set))
            .setDescription(getString(R.string.import_card_set_description))
            .setOverlayPoint(createCardSetHintOverlay)
            .build()
    }

    override fun isEditMode() = getCardSetArg() != null

    companion object {

        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(cardSet: CardSet? = null): CardSetEditorFragment {
            val fragment = CardSetEditorFragment()
            cardSet?.let { fragment.putCardSetArg(it) }
            return fragment
        }

        private fun CardSetEditorFragment.putCardSetArg(cardSet: CardSet) {
            arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
        }

        private fun CardSetEditorFragment.getCardSetArg(): CardSet? =
            arguments?.getParcelable(CARD_SET_KEY)
    }
}