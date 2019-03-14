package com.cardemory.carddata

interface CardRepository {

    suspend fun getAllCards(): List<Card>
}