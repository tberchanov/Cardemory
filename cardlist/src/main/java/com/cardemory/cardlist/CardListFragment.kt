package com.cardemory.cardlist

import com.cardemory.common.mvp.BaseFragment

class CardListFragment :
    BaseFragment<CardListContract.View, CardListContract.Presenter>(),
    CardListContract.View {

    override val layoutResId = R.layout.fragment_cardlist

    companion object {

        fun newInstance() = CardListFragment()
    }
}