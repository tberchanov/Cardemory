package com.cardemory.carddata.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardSet(
    val id: Long,
    val name: String,
    val cards: Map<Long, Card>
) : Parcelable {

    constructor(name: String, cards: List<Card>) : this(
        UNDEFINED_ID,
        name,
        cards.associate { it.id to it }
    )

    companion object {
        const val UNDEFINED_ID = -1L
    }
}