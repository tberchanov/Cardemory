package com.cardemory.train.mvp

import com.cardemory.carddata.entity.Card
import com.cardemory.memorykit.MemoryHolder

class CardHolder(var card: Card) : MemoryHolder {

    override var memoryRank: Double
        get() = card.memoryRank
        set(value) {
            card = card.copy(memoryRank = value)
        }
    override var lastTrainMillis: Long
        get() = card.lastTrainMillis
        set(value) {
            card = card.copy(lastTrainMillis = value)
        }
}