package com.cardemory.carddata.data.json

import com.google.gson.annotations.SerializedName

data class CardJsonEntity(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)