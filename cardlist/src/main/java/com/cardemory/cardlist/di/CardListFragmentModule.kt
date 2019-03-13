package com.cardemory.cardlist.di

import com.cardemory.cardlist.CardListContract
import com.cardemory.cardlist.CardListPresenter
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class CardListFragmentModule {

    @FragmentScope
    @Provides
    internal fun providePresenter(
    ): CardListContract.Presenter {
        return CardListPresenter()
    }
}