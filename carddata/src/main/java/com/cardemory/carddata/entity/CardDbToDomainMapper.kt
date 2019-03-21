package com.cardemory.carddata.entity

import com.cardemory.carddata.data.db.card.CardDbEntity
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardDbToDomainMapper @Inject constructor() : Mapper<CardDbEntity, Card> {

    override fun from(item: CardDbEntity) = item.run {
        Card(id!!, setId, title, description)
    }

    override fun to(item: Card) = item.run {
        CardDbEntity(
            id.takeUnless { it == Card.UNDEFINED_ID },
            cardSetId,
            title,
            description
        )
    }
}