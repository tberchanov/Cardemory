package com.cardemory.infrastructure

import com.cardemory.infrastructure.entity.Either
import com.cardemory.infrastructure.entity.Failure

abstract class BaseBlockingInteractor<out Type, in Params>
    : BaseInteractor<Type, Params>() where Type : Any {

    operator fun invoke(params: Params) = run(params)

    abstract fun run(params: Params): Either<Failure, Type>
}