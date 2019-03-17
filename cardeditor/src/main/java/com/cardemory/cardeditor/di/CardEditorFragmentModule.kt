package com.cardemory.cardeditor.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.cardeditor.CardEditorContract
import com.cardemory.cardeditor.CardEditorNavigation
import com.cardemory.cardeditor.CardEditorPresenter
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardEditorNavigation: CardEditorNavigation,
        saveCardInteractor: SaveCardInteractor
    ): CardEditorContract.Presenter =
        CardEditorPresenter(
            cardEditorNavigation,
            saveCardInteractor
        )
}