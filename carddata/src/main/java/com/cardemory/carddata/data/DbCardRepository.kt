package com.cardemory.carddata.data

import com.cardemory.carddata.data.db.CardDao
import com.cardemory.carddata.entity.CardDbToDomainMapper

class DbCardRepository(
    private val cardDao: CardDao,
    private val cardDbToDomainMapper: CardDbToDomainMapper
) : CardRepository {

    override suspend fun getAllCards() =
        cardDao.getAll().map(cardDbToDomainMapper::from)
}