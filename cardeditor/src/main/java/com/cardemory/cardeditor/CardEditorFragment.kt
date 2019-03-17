package com.cardemory.cardeditor

import android.os.Bundle
import android.view.View
import com.cardemory.carddata.entity.Card
import com.cardemory.common.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_cardeditor.*

class CardEditorFragment :
    BaseFragment<CardEditorContract.View, CardEditorContract.Presenter>(),
    CardEditorContract.View {

    override val layoutResId = R.layout.fragment_cardeditor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveCardButton.setOnClickListener {
            presenter.onSaveCardClicked(getCard())
        }
    }

    private fun getCard(): Card {
        return Card(
            cardDescriptionEditText.text.toString(),
            cardTitleEditText.text.toString()
        )
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