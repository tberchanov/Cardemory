package com.cardemory.carddata.data.db.cardset

import androidx.room.*



@Dao
interface CardSetDao {

    @Query("SELECT * FROM card_set")
    suspend fun getAll(): List<CardSetDbEntity>

    @Query("SELECT * FROM card_set WHERE card_set_id = :cardSetId")
    suspend fun findById(cardSetId: Long): CardSetDbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cardSet: CardSetDbEntity): Long

    @Delete
    suspend fun delete(cardSets: List<CardSetDbEntity>): Int
}
