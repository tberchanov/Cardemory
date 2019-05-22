package com.cardemory.carddata.data

import android.annotation.SuppressLint
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
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
                STUB_CARD_SET_ID,
                "Title: $counter",
                "Description, can be long: $counter",
                42.42,
                100
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

    override suspend fun saveCards(cards: List<Card>) {
        cards.forEach {
            saveCard(it)
        }
    }

    override suspend fun getAllCardSets(): List<CardSet> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getCardSet(cardSetId: Long): CardSet {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveCardSet(cardSet: CardSet): CardSet {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private const val REPOSITORY_OPERATION_DELAY = 1000L
        private const val STUB_CARD_SET_ID = 0L
    }
}