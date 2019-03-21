package com.cardemory.cardeditor.mvp

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cardemory.carddata.entity.Card
import com.cardemory.cardeditor.R
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.hideKeyboard
import com.cardemory.common.util.showKeyboard
import kotlinx.android.synthetic.main.fragment_cardeditor.*

class CardEditorFragment :
    BaseFragment<CardEditorContract.View, CardEditorContract.Presenter>(),
    CardEditorContract.View {

    override val layoutResId = R.layout.fragment_cardeditor

    override val titleRes =
        if (getCardArg() == null)
            R.string.title_create
        else
            R.string.title_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveCardButton.setOnClickListener {
            presenter.onSaveCardClicked(getCard())
        }

        showCardDataIfNeeded()

        cardTitleEditText.showKeyboard()
    }

    private fun showCardDataIfNeeded() {
        getCardArg()?.also {
            cardTitleEditText.setText(it.title)
            cardDescriptionEditText.setText(it.description)
        }
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

    private fun setBackButtonVisibility(visible: Boolean) {
        (activity as? AppCompatActivity)?.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(visible)
            setDisplayShowHomeEnabled(visible)
        }
    }

    private fun getCard(): Card {
        return Card(
            getCardArg()?.id ?: Card.UNDEFINED_ID,
            TODO("After adding cardsets, current cardset will be saved in this fragment"),
            cardTitleEditText.text.toString(),
            cardDescriptionEditText.text.toString()
        )
    }

    override fun hideKeyboard() {
        cardTitleEditText.hideKeyboard()
    }

    companion object {

        private const val CARD_KEY = "CARD_KEY"

        fun newInstance() = CardEditorFragment()

        fun newInstance(card: Card): CardEditorFragment {
            val fragment = CardEditorFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CARD_KEY, card)
            }
            return fragment
        }

        private fun CardEditorFragment.getCardArg(): Card? =
            arguments?.getParcelable(CARD_KEY)
    }
}