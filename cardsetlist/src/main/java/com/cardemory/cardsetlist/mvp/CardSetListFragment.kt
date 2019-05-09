package com.cardemory.cardsetlist.mvp

import android.os.Bundle
import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.cardsetlist.ui.CardSetListAdapter
import com.cardemory.common.mvp.BaseFragment
import com.cardemory.common.util.EmptyMessageObserver
import kotlinx.android.synthetic.main.fragment_card_set_list.*

class CardSetListFragment :
    BaseFragment<CardSetListContract.View, CardSetListContract.Presenter>(),
    CardSetListContract.View {

    private var cardSetAdapter: CardSetListAdapter? = null

    private lateinit var emptyMessageObserver: EmptyMessageObserver

    override val layoutResId = R.layout.fragment_card_set_list
    override val titleRes = R.string.title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createCardSetButton.setOnClickListener {
            presenter.onCreateCardSetClicked()
        }

        cardSetAdapter = CardSetListAdapter(::onCardSetClicked)
        cardSetsRecyclerView.adapter = cardSetAdapter
        emptyMessageObserver =
            EmptyMessageObserver(cardSetAdapter!!, firstCardSetLabel, arrowImageView)
        cardSetAdapter!!.registerAdapterDataObserver(emptyMessageObserver)
    }

    private fun onCardSetClicked(cardSet: CardSet) {
        presenter.onCardSetClicked(cardSet)
    }

    override fun showCardSets(cardSets: List<CardSet>) {
        cardSetAdapter?.swapData(cardSets)
        if (cardSets.isEmpty()) {
            firstCardSetLabel.visibility = View.VISIBLE
            arrowImageView.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        cardSetAdapter!!.unregisterAdapterDataObserver(emptyMessageObserver)
        cardSetAdapter = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() = CardSetListFragment()
    }
}
