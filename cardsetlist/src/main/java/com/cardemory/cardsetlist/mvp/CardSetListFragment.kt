package com.cardemory.cardsetlist.mvp

import com.cardemory.cardsetlist.R
import com.cardemory.common.mvp.BaseFragment

class CardSetListFragment :
    BaseFragment<CardSetListContract.View, CardSetListContract.Presenter>(),
    CardSetListContract.View {

    override val layoutResId = R.layout.fragment_card_set_list

    companion object {

        fun newInstance() = CardSetListFragment()
    }
}