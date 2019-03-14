package com.cardemory.cardlist

import com.cardemory.carddata.CardRepository
import com.cardemory.common.mvp.BasePresenter
import kotlinx.coroutines.launch

class CardListPresenter(
    private val navigation: CardListNavigation,
    private val cardRepository: CardRepository
) : BasePresenter<CardListContract.View>(),
    CardListContract.Presenter {

    override fun attachView(view: CardListContract.View) {
        super.attachView(view)

        navigation.showCreateCardScreen()

        launch {
            val cards = cardRepository.getAllCards()
        }
    }
}