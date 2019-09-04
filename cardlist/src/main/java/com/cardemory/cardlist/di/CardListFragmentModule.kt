package com.cardemory.cardlist.di

import com.cardemory.carddata.interactor.DeleteCardsInteractor
import com.cardemory.carddata.interactor.GetCardSetInteractor
import com.cardemory.carddata.interactor.SaveCardSetToFileInteractor
import com.cardemory.cardlist.mvp.CardListContract
import com.cardemory.cardlist.mvp.CardListPresenter
import com.cardemory.cardlist.navigation.CardListNavigation
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.util.ProgressInteractorExecutor
import com.cardemory.memory_label.di.MemoryLabelModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
@Module(includes = [MemoryLabelModule::class])
class CardListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardListNavigation: CardListNavigation,
        getCardSetInteractor: GetCardSetInteractor,
        saveCardSetToFileInteractor: SaveCardSetToFileInteractor,
        deleteCardsInteractor: DeleteCardsInteractor,
        progressInteractorExecutor: ProgressInteractorExecutor,
        readBooleanInteractor: ReadBooleanInteractor,
        writeBooleanInteractor: WriteBooleanInteractor
    ): CardListContract.Presenter {
        return CardListPresenter(
            cardListNavigation,
            getCardSetInteractor,
            saveCardSetToFileInteractor,
            deleteCardsInteractor,
            progressInteractorExecutor,
            readBooleanInteractor,
            writeBooleanInteractor
        )
    }
}