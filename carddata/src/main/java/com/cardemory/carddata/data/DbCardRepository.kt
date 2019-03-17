package com.cardemory.carddata.data

import com.cardemory.carddata.data.db.CardDao
import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardDbToDomainMapper
import timber.log.Timber

class DbCardRepository(
    private val cardDao: CardDao,
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
}