package com.cardemory.cardlist.di

import com.cardemory.carddata.CardRepository
import com.cardemory.carddata.CollectionCardRepository
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
        cardRepository: CardRepository
    ): CardListContract.Presenter {
        return CardListPresenter(cardListNavigation, cardRepository)
    }

    @FragmentScope
    @Provides
    fun provideCardRepository(): CardRepository {
        return CollectionCardRepository()
    }
}