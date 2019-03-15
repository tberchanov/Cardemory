package com.cardemory.carddata.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    suspend fun getAll(): List<CardDbEntity>

    @Query("SELECT * FROM card where card_id = :id")
    suspend fun findById(id: Long): CardDbEntity?

    @Insert
    suspend fun insert(entity: CardDbEntity): Long
}