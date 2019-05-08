package com.cardemory.ocr.di.module

import com.cardemory.ocr.di.qualifier.MlKitOcr
import com.cardemory.ocr.di.qualifier.TesseractOcr
import com.cardemory.ocr.interactor.BaseRecognizeTextInteractor
import com.cardemory.ocr.interactor.MlKitRecognizeTextInteractor
import com.cardemory.ocr.interactor.TesseractRecognizeTextInteractor
import dagger.Binds
import dagger.Module

@Module
interface TextRecognitionModule {

    @Binds
    @MlKitOcr
    fun provieMlKitRecognizeTextInteractor(
        recognizeTextInteractor: MlKitRecognizeTextInteractor
    ): BaseRecognizeTextInteractor

    @Binds
    @TesseractOcr
    fun provieTesseractRecognizeTextInteractor(
        recognizeTextInteractor: TesseractRecognizeTextInteractor
    ): BaseRecognizeTextInteractor
}
