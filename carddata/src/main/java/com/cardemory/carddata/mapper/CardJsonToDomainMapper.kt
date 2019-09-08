package com.cardemory.carddata.mapper

import com.cardemory.carddata.data.json.CardJsonEntity
import com.cardemory.carddata.entity.Card
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardJsonToDomainMapper @Inject constructor() : Mapper<CardJsonEntity, Card> {

    override fun to(item: Card) = CardJsonEntity(item.title, item.description)

    override fun from(item: CardJsonEntity) = Card(item.title, item.description)
}