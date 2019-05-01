package com.cardemory.carddata.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: Long = UNDEFINED_ID,
    val cardSetId: Long,
    val title: String,
    val description: String,
    val memoryRank: Double,
    val lastTrainMillis: Long
) : Parcelable {

    constructor() : this(UNDEFINED_ID, -1, "", "", 0.0, 0)

    companion object {
        const val UNDEFINED_ID = -1L
    }
}