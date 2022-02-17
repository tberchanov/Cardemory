package com.cardemory.carddata.mapper

import com.cardemory.carddata.data.json.CardSetJsonEntity
import com.cardemory.carddata.entity.CardSet
import com.cardemory.infrastructure.entity.Mapper
import javax.inject.Inject

class CardSetJsonToDomainMapper
@Inject constructor(
    private val cardJsonMapper: CardJsonToDomainMapper,
    private val cardJsonToDomainListMapper: CardJsonToDomainListMapper
) : Mapper<CardSetJsonEntity, CardSet> {

    override fun to(item: CardSet) =
        CardSetJsonEntity(
            item.name,
            item.cards.values.map(cardJsonMapper::to)
        )

    override fun from(item: CardSetJsonEntity) =
        CardSet(
            item.name,
            cardJsonToDomainListMapper.from(item.cards)
        )
}