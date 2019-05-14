package com.cardemory.carddata.data.db.card

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    suspend fun getAll(): List<CardDbEntity>

    @Query("SELECT * FROM card where card_id = :id")
    suspend fun findById(id: Long): CardDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: CardDbEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(cards: List<CardDbEntity>): List<Long>
}