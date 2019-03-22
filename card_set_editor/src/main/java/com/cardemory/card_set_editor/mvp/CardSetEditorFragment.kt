package com.cardemory.card_set_editor.mvp

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.cardemory.card_set_editor.R
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.hideKeyboard
import com.cardemory.common.util.showKeyboard
import kotlinx.android.synthetic.main.fragment_card_set_editor.*

class CardSetEditorFragment :
    BaseFragment<CardSetEditorContract.View, CardSetEditorContract.Presenter>(),
    CardSetEditorContract.View {

    override val layoutResId = R.layout.fragment_card_set_editor

    override val titleRes =
        if (getCardSetArg() == null)
            R.string.title_create
        else
            R.string.title_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardSetNameEditText.showKeyboard()
    }

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

        private fun CardSetEditorFragment.getCardSetArg(): Card? =
            arguments?.getParcelable(CARD_SET_KEY)
    }
}