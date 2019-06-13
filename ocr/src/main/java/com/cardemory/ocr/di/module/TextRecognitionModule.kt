package com.cardemory.ocr.di.module

import com.cardemory.ocr.di.qualifier.MlKitOcr
import com.cardemory.ocr.interactor.BaseRecognizeTextInteractor
import com.cardemory.ocr.interactor.MlKitRecognizeTextInteractor
import dagger.Binds
import dagger.Module

@Module
interface TextRecognitionModule {

    @Binds
    @MlKitOcr
    fun provideMlKitRecognizeTextInteractor(
        recognizeTextInteractor: MlKitRecognizeTextInteractor
    ): BaseRecognizeTextInteractor
}
