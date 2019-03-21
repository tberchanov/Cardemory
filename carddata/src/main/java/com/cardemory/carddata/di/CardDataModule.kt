package com.cardemory.carddata.di

import android.content.Context
import androidx.room.Room
import com.cardemory.carddata.data.CardRepository
import com.cardemory.carddata.data.CollectionCardRepository
import com.cardemory.carddata.data.DbCardRepository
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.di.qualifier.CollectionData
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.entity.CardDbToDomainMapper
import dagger.Module
import dagger.Provides

@Module
class CardDataModule {

    @Provides
    fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    fun provideCardDao(database: AppDatabase) = database.cardDao()

    @Provides
    fun provideCardSetDao(database: AppDatabase) = database.cardSetDao()

    @DbData
    @Provides
    fun provideCardDbRepository(
        cardDao: CardDao,
        cardSetDao: CardSetDao,
        cardDbToDomainMapper: CardDbToDomainMapper
    ): CardRepository = DbCardRepository(
        cardDao,
        cardSetDao,
        cardDbToDomainMapper
    )

    @CollectionData
    @Provides
    fun provideCollectionCardRepository(): CardRepository = CollectionCardRepository()

    companion object {
        private const val DATABASE_NAME = "app_database"
    }
}