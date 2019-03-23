package com.cardemory.carddata.data.db.cardset

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardSetDao {

    @Query("SELECT * FROM card_set")
    suspend fun getAll(): List<CardSetDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: CardSetDbEntity): Long
}