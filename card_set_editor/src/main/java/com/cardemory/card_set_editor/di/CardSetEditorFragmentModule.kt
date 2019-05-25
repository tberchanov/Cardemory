package com.cardemory.card_set_editor.di

import com.cardemory.card_set_editor.mvp.CardSetEditorContract
import com.cardemory.card_set_editor.mvp.CardSetEditorPresenter
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.GetCardSetFromFileInteractor
import com.cardemory.carddata.interactor.SaveCardSetInteractor
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardSetEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardSetEditorNavigation: CardSetEditorNavigation,
        saveCardSetInteractor: SaveCardSetInteractor,
        getCardSetFromFileInteractor: GetCardSetFromFileInteractor
    ): CardSetEditorContract.Presenter =
        CardSetEditorPresenter(
            cardSetEditorNavigation,
            saveCardSetInteractor,
            getCardSetFromFileInteractor
        )
}