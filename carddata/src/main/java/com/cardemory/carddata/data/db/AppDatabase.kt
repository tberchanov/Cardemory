package com.cardemory.carddata.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.card.CardDbEntity
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.data.db.cardset.CardSetDbEntity

@Database(
    entities = [CardDbEntity::class, CardSetDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun cardSetDao(): CardSetDao
}
