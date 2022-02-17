package com.cardemory.common.util.ext

inline fun <T> Collection<T>.mean(meanValue: T.() -> Double): Double {
    var sum = 0.0
    forEach { sum += it.meanValue() }
    return if (isEmpty()) {
        throw IllegalStateException("Collection is empty!")
    } else {
        sum / size
    }
}