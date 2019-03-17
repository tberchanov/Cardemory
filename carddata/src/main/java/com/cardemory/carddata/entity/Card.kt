package com.cardemory.carddata.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: Long,
    val title: String,
    val description: String
) : Parcelable {

    constructor(
        title: String,
        description: String
    ) : this(
        UNDEFINED_ID,
        title,
        description
    )

    companion object {
        const val UNDEFINED_ID = -1L
    }
}