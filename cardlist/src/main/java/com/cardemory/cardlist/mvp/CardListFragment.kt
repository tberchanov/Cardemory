package com.cardemory.cardlist.mvp

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardlist.R
import com.cardemory.cardlist.ui.CardListAdapter
import com.cardemory.common.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_cardlist.*

class CardListFragment :
    BaseFragment<CardListContract.View, CardListContract.Presenter>(),
    CardListContract.View {

    private var cardAdapter: CardListAdapter? = null

    override val layoutResId = R.layout.fragment_cardlist

    override val titleRes = R.string.title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardSet = getCardSetArg()

        createCardButton.setOnClickListener {
            presenter.onCreateCardClicked(cardSet)
        }

        cardAdapter = CardListAdapter(::onCardClicked)
        cardsRecyclerView.adapter = cardAdapter

        presenter.showCards(cardSet)
    }

    private fun onCardClicked(card: Card) {
        presenter.onCardClicked(card)
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

    override fun showCards(cards: List<Card>) {
        cardAdapter?.swapData(cards)
    }

    override fun onDestroyView() {
        cardAdapter = null
        super.onDestroyView()
    }

    companion object {

        private const val CARD_SET_KEY = "CARD_SET_KEY"

        fun newInstance(cardSet: CardSet): CardListFragment {
            val fragment = CardListFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(CARD_SET_KEY, cardSet)
            }
            return fragment
        }

        private fun CardListFragment.getCardSetArg(): CardSet =
            arguments!!.getParcelable(CARD_SET_KEY)!!
    }
}