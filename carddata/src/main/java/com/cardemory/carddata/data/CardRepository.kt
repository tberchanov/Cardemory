package com.cardemory.carddata.data

import com.cardemory.carddata.entity.Card

interface CardRepository {

    suspend fun getAllCards(): List<Card>
}