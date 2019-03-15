package com.cardemory.cardlist.di

import com.cardemory.carddata.data.CardRepository
import com.cardemory.carddata.data.CollectionCardRepository
import com.cardemory.carddata.interactor.GetAllCardsInteractor
import com.cardemory.cardlist.CardListContract
import com.cardemory.cardlist.CardListNavigation
import com.cardemory.cardlist.CardListPresenter
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CardListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardListNavigation: CardListNavigation,
        getAllCardsInteractor: GetAllCardsInteractor
    ): CardListContract.Presenter {
        return CardListPresenter(cardListNavigation, getAllCardsInteractor)
    }

    @FragmentScope
    @Provides
    fun provideCardRepository(): CardRepository {
        return CollectionCardRepository()
    }
}