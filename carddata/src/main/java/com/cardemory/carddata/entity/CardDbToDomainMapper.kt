package com.cardemory.carddata.entity

import com.cardemory.carddata.data.db.CardDbEntity
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardDbToDomainMapper @Inject constructor() : Mapper<CardDbEntity, Card> {

    override fun from(item: CardDbEntity) = item.run {
        Card(id, title, description)
    }

    override fun to(item: Card) = item.run {
        CardDbEntity(id, title, description)
    }
}