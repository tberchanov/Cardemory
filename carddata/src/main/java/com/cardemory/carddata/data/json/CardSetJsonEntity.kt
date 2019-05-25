package com.cardemory.carddata.data.json

import com.google.gson.annotations.SerializedName

data class CardSetJsonEntity(
    @SerializedName("name") val name: String,
    @SerializedName("cards") val cards: List<CardJsonEntity>
)