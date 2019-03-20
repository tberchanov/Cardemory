package com.cardemory.cardlist.mvp

import android.os.Bundle
import android.view.View
import com.cardemory.carddata.entity.Card
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

        createCardButton.setOnClickListener {
            presenter.onCreateCardClicked()
        }

        cardAdapter = CardListAdapter(::onCardClicked)
        recyclerView.adapter = cardAdapter
    }

    private fun onCardClicked(card: Card) {
        presenter.onCardClicked(card)
    }

    override fun showCards(cards: List<Card>) {
        cardAdapter?.swapData(cards)
    }

    override fun onDestroyView() {
        cardAdapter = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() = CardListFragment()
    }
}