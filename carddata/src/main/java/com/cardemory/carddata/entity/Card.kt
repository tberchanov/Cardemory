package com.cardemory.carddata.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Card(
    val id: Long = UNDEFINED_ID,
    val cardSetId: Long,
    val title: String,
    val description: String
) : Parcelable {

    companion object {
        const val UNDEFINED_ID = -1L
    }
}