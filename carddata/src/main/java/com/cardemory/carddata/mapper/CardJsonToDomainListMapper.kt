package com.cardemory.carddata.mapper

import com.cardemory.carddata.data.json.CardJsonEntity
import com.cardemory.carddata.entity.Card
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardJsonToDomainListMapper
@Inject constructor() : Mapper<List<CardJsonEntity>, List<Card>> {

    override fun from(item: List<CardJsonEntity>): List<Card> {
        var undefinedId = -1L
        return item.map {
            Card(undefinedId--, Card.UNDEFINED_ID, it.title, it.description, 0.0, 0)
        }
    }
}