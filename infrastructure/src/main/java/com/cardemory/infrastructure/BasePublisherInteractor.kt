package com.cardemory.infrastructure

import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach

@ObsoleteCoroutinesApi
abstract class BasePublisherInteractor<out Type, in Params>
    : BaseInteractor<Type, Params>() where Type : Any {

    private var job: Job? = null

    operator fun invoke(
        params: Params,
        onResult: (Either<Failure, Type>) -> Unit = {},
        onComplete: (failure: Failure?) -> Unit = {}
    ) {
        job = GlobalScope.launch(Dispatchers.Default) {
            var failure: Failure? = null
            try {
                run(params).consumeEach {
                    withContext(Dispatchers.Main) {
                        onResult.invoke(it)
                    }
                    if (it is Either.Left) {
                        failure = it.valueLeft
                    }
                }
            } finally {
                GlobalScope.launch(Dispatchers.Main) {
                    onComplete.invoke(failure)
                }
            }
        }
    }

    open fun cancel() {
        job?.cancel()
    }

    fun isCanceled(): Boolean {
        return job?.isCancelled ?: false
    }

    fun isActive(): Boolean {
        return job?.isActive ?: false
    }

    abstract suspend fun run(params: Params): ReceiveChannel<Either<Failure, Type>>
}