package com.cardemory.carddata.entity

import com.cardemory.carddata.data.db.cardset.CardSetDbEntity
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardSetDbToDomainMapper
@Inject constructor() : Mapper<CardSetDbEntity, CardSet> {

    override fun to(item: CardSet) = item.run {
        CardSetDbEntity(
            id.takeUnless { it == CardSet.UNDEFINED_ID },
            name
        )
    }

    override fun from(item: CardSetDbEntity) = item.run {
        CardSet(id!!, name, emptyMap())
    }
}