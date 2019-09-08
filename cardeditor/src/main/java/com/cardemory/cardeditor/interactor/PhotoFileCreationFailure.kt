package com.cardemory.cardeditor.interactor

import com.cardemory.infrastructure.entity.Failure
import java.io.IOException

class PhotoFileCreationFailure(exception: IOException) : Failure.FeatureFailure(exception)
