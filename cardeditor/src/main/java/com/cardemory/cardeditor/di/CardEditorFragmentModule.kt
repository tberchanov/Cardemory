package com.cardemory.cardeditor.di

import com.cardemory.carddata.interactor.SaveCardInteractor
import com.cardemory.cardeditor.interactor.GetPhotoFileInteractor
import com.cardemory.cardeditor.mvp.CardEditorContract
import com.cardemory.cardeditor.mvp.CardEditorPresenter
import com.cardemory.cardeditor.navigation.CardEditorNavigation
import com.cardemory.cardeditor.ui.tutorial.CardEditorTutorialSpotlight
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.common.util.ProgressInteractorExecutor
import com.cardemory.ocr.di.module.TextRecognitionModule
import com.cardemory.ocr.di.qualifier.MlKitOcr
import com.cardemory.ocr.interactor.BaseRecognizeTextInteractor
import dagger.Module
import dagger.Provides

@Module(includes = [TextRecognitionModule::class])
class CardEditorFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        cardEditorNavigation: CardEditorNavigation,
        saveCardInteractor: SaveCardInteractor,
        getPhotoFileInteractor: GetPhotoFileInteractor,
        @MlKitOcr recognizeTextInteractor: BaseRecognizeTextInteractor,
        progressInteractorExecutor: ProgressInteractorExecutor,
        readBooleanInteractor: ReadBooleanInteractor,
        writeBooleanInteractor: WriteBooleanInteractor,
        cardEditorTutorialSpotlight: CardEditorTutorialSpotlight
    ): CardEditorContract.Presenter =
        CardEditorPresenter(
            cardEditorNavigation,
            saveCardInteractor,
            getPhotoFileInteractor,
            recognizeTextInteractor,
            progressInteractorExecutor,
            readBooleanInteractor,
            writeBooleanInteractor,
            cardEditorTutorialSpotlight
        )
}