package com.cardemory.carddata.data

import android.annotation.SuppressLint
import com.cardemory.carddata.entity.Card
import kotlinx.coroutines.delay
import java.util.*

class CollectionCardRepository : CardRepository {

    @SuppressLint("UseSparseArrays")
    private val cards = HashMap<Long, Card>()

    private val random = Random()

    init {
        repeat(10) { counter ->
            Card(
                counter.toLong(),
                "Title: $counter",
                "Description, can be long: $counter"
            ).let { cards.put(it.id, it) }
        }
    }

    override suspend fun getAllCards(): List<Card> {
        delay(REPOSITORY_OPERATION_DELAY)
        return cards.values.toList()
    }

    override suspend fun saveCard(card: Card): Card {
        val id = cards[card.id]?.id ?: random.nextLong()
        return card.copy(id = id).also {
            cards[it.id] = it
        }
    }

    companion object {
        private const val REPOSITORY_OPERATION_DELAY = 1000L
    }
}