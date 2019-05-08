package com.cardemory.ocr.interactor

import com.cardemory.infrastructure.entity.Failure

class TextRecognitionFailure(throwable: Throwable) : Failure.FeatureFailure(throwable)