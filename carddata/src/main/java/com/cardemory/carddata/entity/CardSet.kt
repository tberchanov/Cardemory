package com.cardemory.carddata.entity

data class CardSet(
    val id: Long,
    val name: String,
    val cards: Map<Long, Card>
)