package com.cardemory.ocr.interactor

import android.net.Uri
import com.cardemory.infrastructure.BaseSingleInteractor

abstract class BaseRecognizeTextInteractor : BaseSingleInteractor<String, Uri>()