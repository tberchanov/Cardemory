package com.cardemory.common.domain

sealed class Failure(val cause: Throwable?) {

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(cause: Throwable? = null) : Failure(cause)

    override fun toString(): String {
        return "Failure(cause=$cause)"
    }
}