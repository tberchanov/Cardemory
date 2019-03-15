package com.cardemory.cardlist.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.GetAllCardsInteractor
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
        getAllCardsInteractor: GetAllCardsInteractor
    ): CardListContract.Presenter {
        return CardListPresenter(
            cardListNavigation,
            getAllCardsInteractor
        )
    }
}