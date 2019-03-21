package com.cardemory.cardsetlist.mvp

import android.os.Bundle
import android.view.View
import com.cardemory.carddata.entity.CardSet
import com.cardemory.cardsetlist.R
import com.cardemory.cardsetlist.ui.CardSetListAdapter
import com.cardemory.common.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_card_set_list.*

class CardSetListFragment :
    BaseFragment<CardSetListContract.View, CardSetListContract.Presenter>(),
    CardSetListContract.View {

    private var cardSetAdapter: CardSetListAdapter? = null

    override val layoutResId = R.layout.fragment_card_set_list
    override val titleRes = R.string.title

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardSetAdapter = CardSetListAdapter(::onCardSetClicked)
        cardSetsRecyclerView.adapter = cardSetAdapter
    }

    private fun onCardSetClicked(cardSet: CardSet) {
        TODO()
    }

    override fun onDestroyView() {
        cardSetAdapter = null
        super.onDestroyView()
    }

    companion object {

        fun newInstance() = CardSetListFragment()
    }
}