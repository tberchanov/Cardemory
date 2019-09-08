package com.cardemory.common.interactor

import com.cardemory.common.data.KeyValueRepository
import com.cardemory.common.interactor.failure.WriteKeyValueFailure
import com.cardemory.infrastructure.BaseSingleInteractor
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import javax.inject.Inject

class WriteBooleanInteractor
@Inject constructor(
    private val keyValueRepository: KeyValueRepository
) : BaseSingleInteractor<Unit, WriteBooleanInteractor.Params>() {

    operator fun invoke(key: String, value: Boolean, onResult: (Either<Failure, Unit>) -> Unit) {
        invoke(Params(key, value), onResult)
    }

    override suspend fun run(params: Params) =
        if (keyValueRepository.writeValue(params.key, params.value))
            Either.Right(Unit)
        else
            Either.Left(WriteKeyValueFailure(params.key, params.value))

    data class Params(
        val key: String,
        val value: Boolean
    )
}