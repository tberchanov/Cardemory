package com.cardemory.memory_label.cardset

import com.cardemory.carddata.entity.CardSet
import com.cardemory.common.util.ext.mean
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.MAX_MEMORY_LABEL_THREESOLD
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.MIDDLE_LABEL_THREESOLD
import com.cardemory.memory_label.MemoryLabelTransformer
import com.cardemory.memory_label.R
import javax.inject.Inject

class HighCardSetMemoryLabelTransformer
@Inject constructor() : MemoryLabelTransformer<CardSet> {

    override fun transform(memoryHolder: CardSet): Int? {
        val meanMemoryRank = memoryHolder.cards.values.mean { memoryRank }
        return if (meanMemoryRank in MIDDLE_LABEL_THREESOLD..MAX_MEMORY_LABEL_THREESOLD) {
            R.drawable.ic_high_memory_label
        } else {
            null
        }
    }
}