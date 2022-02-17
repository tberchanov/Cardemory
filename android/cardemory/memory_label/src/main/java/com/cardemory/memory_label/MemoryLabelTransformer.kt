package com.cardemory.memory_label

import androidx.annotation.DrawableRes
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet

interface MemoryLabelTransformer<T> {

    @DrawableRes
    fun transform(memoryHolder: T): Int?
}

open class BaseMemoryLabelTransformer<T>(
    private val transformers: List<MemoryLabelTransformer<T>>
) : MemoryLabelTransformer<T> {

    override fun transform(memoryHolder: T): Int? {
        transformers.forEach { transformer ->
            transformer.transform(memoryHolder)?.let { return it }
        }
        return null
    }

    companion object {
        const val MIN_MEMORY_LABEL_THREESOLD = 0.0
        const val MAX_MEMORY_LABEL_THREESOLD = 1.0
        const val LOW_MEMORY_LABEL_THREESOLD = 0.4
        const val MIDDLE_LABEL_THREESOLD = 0.8
    }
}

class CardMemoryLabelTransformer(
    transformers: List<MemoryLabelTransformer<Card>>
) : BaseMemoryLabelTransformer<Card>(transformers)

class CardSetMemoryLabelTransformer(
    transformers: List<MemoryLabelTransformer<CardSet>>
) : BaseMemoryLabelTransformer<CardSet>(transformers)