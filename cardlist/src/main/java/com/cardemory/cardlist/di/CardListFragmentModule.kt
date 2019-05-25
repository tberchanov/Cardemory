package com.cardemory.cardlist.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.GetCardSetInteractor
import com.cardemory.carddata.interactor.SaveCardSetToFileInteractor
import com.cardemory.cardlist.mvp.CardListContract
import com.cardemory.cardlist.mvp.CardListPresenter
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.RequestPermissionsInteractor
import com.cardemory.common.util.ProgressInteractorExecutor
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@Module(includes = [CardDataModule::class])
class CardListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardListNavigation: CardListNavigation,
        getCardSetInteractor: GetCardSetInteractor,
        saveCardSetToFileInteractor: SaveCardSetToFileInteractor,
        progressInteractorExecutor: ProgressInteractorExecutor,
        requestPermissionsInteractor: RequestPermissionsInteractor
    ): CardListContract.Presenter {
        return CardListPresenter(
            cardListNavigation,
            getCardSetInteractor,
            saveCardSetToFileInteractor,
            progressInteractorExecutor,
            requestPermissionsInteractor
        )
    }
}