package com.cardemory.carddata.data

import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.data.db.cardset.CardSetDbEntity
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardDbToDomainMapper
import com.cardemory.carddata.entity.CardSet
import timber.log.Timber

class DbCardRepository(
    private val cardDao: CardDao,
    private val cardSetDao: CardSetDao,
    private val cardDbToDomainMapper: CardDbToDomainMapper
) : CardRepository {

    override suspend fun getAllCards() =
        cardDao.getAll().map(cardDbToDomainMapper::from)

    override suspend fun saveCard(card: Card): Card {
        val cardId = card
            .let(cardDbToDomainMapper::to)
            .let { cardDao.save(it) }
        return card.copy(id = cardId).also {
            Timber.d("Saved card id: $it")
        }
    }

    override suspend fun getAllCardSets(): List<CardSet> {
        val cardSetsDb = cardSetDao.getAll()
        val cards = getAllCards()
        val cardSets = mutableListOf<CardSet>()
        for (cardSetDb in cardSetsDb) {
            val cardsInSet = cards.filter { it.cardSetId == cardSetDb.id }
            val cardSet = mapToCardSet(cardSetDb, cardsInSet)
            cardSets.add(cardSet)
        }
        return cardSets
    }

    private fun mapToCardSet(
        cardSetDbEntity: CardSetDbEntity,
        cardsInSet: List<Card>
    ): CardSet {
        val cardsMap = mutableMapOf<Long, Card>()
        for (card in cardsInSet) {
            cardsMap[card.id] = card
        }
        return CardSet(cardSetDbEntity.id!!, cardSetDbEntity.name, cardsMap)
    }
}