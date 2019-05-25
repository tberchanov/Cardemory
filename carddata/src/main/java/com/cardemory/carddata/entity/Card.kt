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

    constructor(
        title: String,
        description: String
    ) : this(UNDEFINED_ID, UNDEFINED_ID, title, description, 0.0, 0)

    constructor() : this(UNDEFINED_ID, UNDEFINED_ID, "", "", 0.0, 0)

    companion object {
        const val UNDEFINED_ID = -1L
    }
}