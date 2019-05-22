package com.cardemory.carddata.data

import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.data.db.cardset.CardSetDbEntity
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardDbToDomainMapper
import com.cardemory.carddata.entity.CardSet
import com.cardemory.carddata.entity.CardSetDbToDomainMapper
import timber.log.Timber

class DbCardRepository(
    private val cardDao: CardDao,
    private val cardSetDao: CardSetDao,
    private val cardDbToDomainMapper: CardDbToDomainMapper,
    private val cardSetDbToDomainMapper: CardSetDbToDomainMapper
) : CardRepository {

    override suspend fun getAllCards() =
        cardDao.getAll().map(cardDbToDomainMapper::from)

    override suspend fun saveCard(card: Card): Card {
        val cardId = cardDbToDomainMapper
            .to(card)
            .let { cardDao.save(it) }
        return card.copy(id = cardId).also {
            Timber.d("Saved card id: $it")
        }
    }

    override suspend fun saveCards(cards: List<Card>) {
        cards.map(cardDbToDomainMapper::to)
            .also { cardDao.saveAll(it) }
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

    override suspend fun getCardSet(cardSetId: Long): CardSet? {
        val cardSetDb = cardSetDao.findById(cardSetId) ?: return null
        val cardsDb = cardDao.findByCardSetId(cardSetId)
            .map(cardDbToDomainMapper::from)
        return mapToCardSet(cardSetDb, cardsDb)
    }

    override suspend fun saveCardSet(cardSet: CardSet): CardSet {
        val cardSetId = cardSetDbToDomainMapper
            .to(cardSet)
            .let { cardSetDao.save(it) }
        return cardSet.copy(id = cardSetId).also {
            Timber.d("Saved card set id: $it")
        }
    }

    private fun mapToCardSet(
        cardSetDbEntity: CardSetDbEntity,
        cardsInSet: List<Card>
    ): CardSet {
        val cardsMap = cardsInSet.associate { it.id to it }
        return CardSet(cardSetDbEntity.id!!, cardSetDbEntity.name, cardsMap)
    }
}