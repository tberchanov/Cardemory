package com.cardemory.carddata.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardSet(
    val id: Long,
    val name: String,
    val cards: Map<Long, Card>
) : Parcelable {

    companion object {
        const val UNDEFINED_ID = -1L
    }
}