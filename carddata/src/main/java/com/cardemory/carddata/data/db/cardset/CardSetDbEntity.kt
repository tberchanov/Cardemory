package com.cardemory.carddata.data.db.cardset

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_set")
data class CardSetDbEntity(
    @PrimaryKey
    @ColumnInfo(name = "card_set_id") var id: Long?,
    @ColumnInfo(name = "name") var name: String
)