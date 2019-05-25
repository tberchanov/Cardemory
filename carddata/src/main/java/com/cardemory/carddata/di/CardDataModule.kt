package com.cardemory.carddata.di

import android.content.Context
import androidx.room.Room
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.data.repository.CardRepository
import com.cardemory.carddata.data.repository.CollectionCardRepository
import com.cardemory.carddata.data.repository.DbCardRepository
import com.cardemory.carddata.di.qualifier.CollectionData
import com.cardemory.carddata.di.qualifier.DbData
import com.cardemory.carddata.mapper.CardDbToDomainMapper
import com.cardemory.carddata.mapper.CardSetDbToDomainMapper
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
        cardDbToDomainMapper: CardDbToDomainMapper,
        cardSetDbToDomainMapper: CardSetDbToDomainMapper
    ): CardRepository =
        DbCardRepository(
            cardDao,
            cardSetDao,
            cardDbToDomainMapper,
            cardSetDbToDomainMapper
        )

    @CollectionData
    @Provides
    fun provideCollectionCardRepository(): CardRepository =
        CollectionCardRepository()

    companion object {
        private const val DATABASE_NAME = "app_database"
    }
}