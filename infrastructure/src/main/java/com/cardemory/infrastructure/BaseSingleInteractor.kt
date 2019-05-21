package com.cardemory.infrastructure

import androidx.annotation.CallSuper
import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure
import kotlinx.coroutines.*

abstract class BaseSingleInteractor<out Type, in Params>
    : BaseInteractor<Type, Params>() where Type : Any {

    private lateinit var job: Deferred<Either<Failure, Type>>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        job = GlobalScope.async(Dispatchers.Default) {
            run(params)
        }

        GlobalScope.launch(Dispatchers.Main) {
            onResult(job.await())
        }
    }

    @CallSuper
    open fun cancel() {
        job.cancel()
    }

    fun isCanceled(): Boolean {
        return ::job.isInitialized && job.isCancelled
    }

    abstract suspend fun run(params: Params): Either<Failure, Type>
}