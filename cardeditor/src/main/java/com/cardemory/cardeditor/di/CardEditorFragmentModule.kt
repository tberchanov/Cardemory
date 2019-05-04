package com.cardemory.cardeditor.di

import com.cardemory.carddata.di.CardDataModule
import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.cardeditor.interactor.GetPhotoFileInteractor
import com.cardemory.cardeditor.mvp.CardEditorContract
import com.cardemory.cardeditor.mvp.CardEditorPresenter
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.ocr.RecognizeTextInteractor
import dagger.Module
import dagger.Provides

@Module(includes = [CardDataModule::class])
class CardEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardEditorNavigation: CardEditorNavigation,
        saveCardInteractor: SaveCardInteractor,
        getPhotoFileInteractor: GetPhotoFileInteractor,
        recognizeTextInteractor: RecognizeTextInteractor
    ): CardEditorContract.Presenter =
        CardEditorPresenter(
            cardEditorNavigation,
            saveCardInteractor,
            getPhotoFileInteractor,
            recognizeTextInteractor
        )
}