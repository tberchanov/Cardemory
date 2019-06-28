package com.cardemory.carddata.data.db.card

import androidx.room.*

@Dao
interface CardDao {

    @Query("SELECT * FROM card")
    suspend fun getAll(): List<CardDbEntity>

    @Query("SELECT * FROM card where card_id = :id")
    suspend fun findById(id: Long): CardDbEntity?

    @Query("SELECT * FROM card where set_id = :cardSetId")
    suspend fun findByCardSetId(cardSetId: Long): List<CardDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: CardDbEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(cards: List<CardDbEntity>): List<Long>

    @Delete
    suspend fun delete(cards: List<CardDbEntity>): Int
}