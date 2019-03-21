package com.cardemory.carddata.data

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet

interface CardRepository {

    suspend fun getAllCards(): List<Card>

    suspend fun saveCard(card: Card): Card

    suspend fun getAllCardSets(): List<CardSet>
}