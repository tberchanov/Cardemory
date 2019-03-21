package com.cardemory.carddata.data.db.card

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardDbEntity(
    @PrimaryKey @ColumnInfo(name = "card_id") var id: Long?,
    @ColumnInfo(name = "set_id") var setId: Long,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String
)