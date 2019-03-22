package com.cardemory.card_set_editor.di

import com.cardemory.card_set_editor.mvp.CardSetEditorContract
import com.cardemory.card_set_editor.mvp.CardSetEditorPresenter
import com.cardemory.card_set_editor.navigation.CardSetEditorNavigation
import com.cardemory.carddata.di.CardDataModule
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardSetEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardSetEditorNavigation: CardSetEditorNavigation
    ): CardSetEditorContract.Presenter =
        CardSetEditorPresenter(
            cardSetEditorNavigation
        )
}