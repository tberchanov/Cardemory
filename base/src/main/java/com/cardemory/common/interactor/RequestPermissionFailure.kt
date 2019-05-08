package com.cardemory.common.interactor

import com.cardemory.infrastructure.entity.Failure

class RequestPermissionFailure(throwable: Throwable? = null) : Failure.FeatureFailure(throwable)