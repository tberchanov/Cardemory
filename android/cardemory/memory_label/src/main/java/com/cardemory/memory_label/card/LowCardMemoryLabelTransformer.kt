package com.cardemory.memory_label.card

import com.cardemory.carddata.entity.Card
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.LOW_MEMORY_LABEL_THREESOLD
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.MIN_MEMORY_LABEL_THREESOLD
import com.cardemory.memory_label.MemoryLabelTransformer
import com.cardemory.memory_label.R
import javax.inject.Inject

class LowCardMemoryLabelTransformer
@Inject constructor() : MemoryLabelTransformer<Card> {

    override fun transform(memoryHolder: Card) =
        if (memoryHolder.memoryRank >= MIN_MEMORY_LABEL_THREESOLD
            && memoryHolder.memoryRank < LOW_MEMORY_LABEL_THREESOLD
        ) {
            R.drawable.ic_low_memory_label
        } else {
            null
        }
}