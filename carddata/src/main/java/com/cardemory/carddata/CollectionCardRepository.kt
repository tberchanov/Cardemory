package com.cardemory.carddata

import kotlinx.coroutines.delay

class CollectionCardRepository : CardRepository {

    private val cards = mutableListOf<Card>()

    init {
        repeat(10) { counter ->
            Card(
                counter.toLong(),
                "Title: $counter",
                "Description, can be long: $counter"
            ).let { cards.add(it) }
        }
    }

    override suspend fun getAllCards(): List<Card> {
        delay(REPOSITORY_OPERATION_DELAY)
        return cards
    }

    companion object {
        private const val REPOSITORY_OPERATION_DELAY = 1000L
    }
}