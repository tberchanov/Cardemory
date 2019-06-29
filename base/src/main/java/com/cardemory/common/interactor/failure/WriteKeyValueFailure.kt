package com.cardemory.common.interactor.failure

import com.cardemory.infrastructure.entity.Failure

data class WriteKeyValueFailure<V>(
    private val key: String,
    private val value: V
) : Failure.FeatureFailure()