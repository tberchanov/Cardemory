package com.cardemory.cardsetlist.mvp

import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.mvp.BasePresenter

class CardSetListPresenter(
    private val cardSetListNavigation: CardSetListNavigation
) : BasePresenter<CardSetListContract.View>(),
    CardSetListContract.Presenter