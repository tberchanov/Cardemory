package com.cardemory.card_set_editor.mvp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.cardemory.card_set_editor.R
import com.cardemory.card_set_editor.mvp.CardSetEditorContract.Companion.SELECT_FILE_REQUEST_CODE
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.hideKeyboard
import com.cardemory.common.util.showKeyboard
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_card_set_editor.*
import timber.log.Timber

class CardSetEditorFragment :
    BaseFragment<CardSetEditorContract.View, CardSetEditorContract.Presenter>(),
    CardSetEditorContract.View {

    private var loadedCardSet: CardSet? = null

    override val layoutResId = R.layout.fragment_card_set_editor

    override val titleRes =
        if (getCardSetArg() == null)
            R.string.title_create_card_set
        else
            R.string.title_edit_edit_card_set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveCardSetButton.setOnClickListener {
            presenter.onSaveCardSetClicked(getCardSet())
        }
        importButton.setOnClickListener {
            presenter.selectFile()
        }
        cardSetNameEditText.showKeyboard()
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