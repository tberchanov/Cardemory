package com.cardemory.infrastructure.entity

sealed class Either<out L, out R> {

    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val valueLeft: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val valueRight: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>

    val isLeft get() = this is Left<L>

    fun <L> left(a: L) = Left(a)

    fun <R> right(b: R) = Right(b)

    fun either(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(valueLeft)
            is Right -> fnR(valueRight)
        }

    fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
        f(this(it))
    }

    fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Left -> Left(
                valueLeft
            )
            is Right -> fn(valueRight)
        }

    fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))
}