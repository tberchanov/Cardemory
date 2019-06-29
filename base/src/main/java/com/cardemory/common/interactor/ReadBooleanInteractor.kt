package com.cardemory.common.interactor

import com.cardemory.common.data.KeyValueRepository
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import javax.inject.Inject

class ReadBooleanInteractor
@Inject constructor(
    private val keyValueRepository: KeyValueRepository
) : BaseSingleInteractor<Boolean, String>() {

    override suspend fun run(params: String) =
        Either.Right(keyValueRepository.readBoolean(params))
}