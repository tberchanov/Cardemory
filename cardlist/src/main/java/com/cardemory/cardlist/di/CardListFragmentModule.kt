package com.cardemory.cardlist.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.GetCardSetInteractor
import com.cardemory.cardlist.mvp.CardListContract
import com.cardemory.cardlist.mvp.CardListPresenter
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardListNavigation: CardListNavigation,
        getCardSetInteractor: GetCardSetInteractor
    ): CardListContract.Presenter {
        return CardListPresenter(
            cardListNavigation,
            getCardSetInteractor
        )
    }
}