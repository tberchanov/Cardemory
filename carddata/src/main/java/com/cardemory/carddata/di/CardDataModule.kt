package com.cardemory.carddata.di

import android.content.Context
import androidx.room.Room
import com.cardemory.carddata.data.CardRepository
import com.cardemory.carddata.data.CollectionCardRepository
import com.cardemory.carddata.data.DbCardRepository
import com.cardemory.carddata.data.db.AppDatabase
import com.cardemory.carddata.data.db.CardDao
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

    @DbData
    @Provides
    fun provideCardDbRepository(
        cardDao: CardDao,
        cardDbToDomainMapper: CardDbToDomainMapper
    ): CardRepository = DbCardRepository(
        cardDao,
        cardDbToDomainMapper
    )

    @CollectionData
    @Provides
    fun provideCollectionCardRepository(): CardRepository = CollectionCardRepository()

    companion object {
        private const val DATABASE_NAME = "app_database"
    }
}