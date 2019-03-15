package com.cardemory.carddata.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CardDbEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao
}
