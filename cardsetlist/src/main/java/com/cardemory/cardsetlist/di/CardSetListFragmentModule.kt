package com.cardemory.cardsetlist.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.mvp.cardsetlist.CardSetListContract
import com.cardemory.cardsetlist.mvp.cardsetlist.CardSetListPresenter
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardSetListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardSetListNavigation: CardSetListNavigation,
        getAllCardSetsInteractor: GetAllCardSetsInteractor
    ): CardSetListContract.Presenter {
        return CardSetListPresenter(
            cardSetListNavigation,
            getAllCardSetsInteractor
        )
    }
}