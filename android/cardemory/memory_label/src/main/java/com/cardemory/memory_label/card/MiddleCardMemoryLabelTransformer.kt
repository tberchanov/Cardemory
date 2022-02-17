package com.cardemory.memory_label.card

import com.cardemory.carddata.entity.Card
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.LOW_MEMORY_LABEL_THREESOLD
import com.cardemory.memory_label.BaseMemoryLabelTransformer.Companion.MIDDLE_LABEL_THREESOLD
import com.cardemory.memory_label.MemoryLabelTransformer
import com.cardemory.memory_label.R
import javax.inject.Inject

class MiddleCardMemoryLabelTransformer
@Inject constructor() : MemoryLabelTransformer<Card> {

    override fun transform(memoryHolder: Card) =
        if (memoryHolder.memoryRank >= LOW_MEMORY_LABEL_THREESOLD
            && memoryHolder.memoryRank < MIDDLE_LABEL_THREESOLD
        ) {
            R.drawable.ic_middle_memory_label
        } else {
            null
        }
}