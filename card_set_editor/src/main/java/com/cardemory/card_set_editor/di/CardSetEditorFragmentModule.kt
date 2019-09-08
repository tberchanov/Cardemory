package com.cardemory.card_set_editor.di

import com.cardemory.card_set_editor.mvp.CardSetEditorContract
import com.cardemory.card_set_editor.mvp.CardSetEditorPresenter
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.card_set_editor.ui.tutorial.CardSetEditorTutorialSpotlight
import com.cardemory.carddata.interactor.GetCardSetFromFileInteractor
import com.cardemory.carddata.interactor.SaveCardSetInteractor
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import dagger.Module
import dagger.Provides

@Module
class CardSetEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardSetEditorNavigation: CardSetEditorNavigation,
        saveCardSetInteractor: SaveCardSetInteractor,
        getCardSetFromFileInteractor: GetCardSetFromFileInteractor,
        readBooleanInteractor: ReadBooleanInteractor,
        writeBooleanInteractor: WriteBooleanInteractor,
        cardSetEditorTutorialSpotlight: CardSetEditorTutorialSpotlight
    ): CardSetEditorContract.Presenter =
        CardSetEditorPresenter(
            cardSetEditorNavigation,
            saveCardSetInteractor,
            getCardSetFromFileInteractor,
            readBooleanInteractor,
            writeBooleanInteractor,
            cardSetEditorTutorialSpotlight
        )
}