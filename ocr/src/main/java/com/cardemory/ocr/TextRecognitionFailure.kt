package com.cardemory.ocr

import com.cardemory.infrastructure.entity.Failure

class TextRecognitionFailure(throwable: Throwable) : Failure.FeatureFailure(throwable)