package com.cardemory.carddata.interactor.failure

import com.cardemory.infrastructure.entity.Failure

class ReadCardSetFromFileFailure(case: Throwable) : Failure.FeatureFailure(case)