package com.cardemory.cardlist

import com.cardemory.common.mvp.BasePresenter

class CardListPresenter(private val navigation: CardListNavigation) :
    BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    override fun attachView(view: CardListContract.View) {
        super.attachView(view)

        navigation.showCreateCardScreen()
    }
}