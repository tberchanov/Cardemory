package com.cardemory.cardsetlist.di

import com.cardemory.carddata.interactor.DeleteCardSetsInteractor
import com.cardemory.carddata.interactor.GetAllCardSetsInteractor
import com.cardemory.cardsetlist.mvp.cardsetlist.CardSetListContract
import com.cardemory.cardsetlist.mvp.cardsetlist.CardSetListPresenter
import com.cardemory.cardsetlist.navigation.CardSetListNavigation
import com.cardemory.cardsetlist.ui.tutorial.CardSetListTutorialSpotlight
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.memory_label.CardSetMemoryLabelTransformer
import com.cardemory.memory_label.di.MemoryLabelModule
import dagger.Module
import dagger.Provides

@Module(includes = [MemoryLabelModule::class])
class CardSetListFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardSetListNavigation: CardSetListNavigation,
        getAllCardSetsInteractor: GetAllCardSetsInteractor,
        deleteCardSetsInteractor: DeleteCardSetsInteractor,
        writeBooleanInteractor: WriteBooleanInteractor,
        readBooleanInteractor: ReadBooleanInteractor,
        cardSetListTutorialSpotlight: CardSetListTutorialSpotlight,
        cardSetMemoryLabelTransformer: CardSetMemoryLabelTransformer
    ): CardSetListContract.Presenter {
        return CardSetListPresenter(
            cardSetListNavigation,
            getAllCardSetsInteractor,
            deleteCardSetsInteractor,
            writeBooleanInteractor,
            readBooleanInteractor,
            cardSetListTutorialSpotlight,
            cardSetMemoryLabelTransformer
        )
    }
}