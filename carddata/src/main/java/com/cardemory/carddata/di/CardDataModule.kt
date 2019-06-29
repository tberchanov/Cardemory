package com.cardemory.carddata.di

import android.content.Context
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
import javax.inject.Singleton

@Module
class CardDataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context) = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideCardDao(database: AppDatabase) = database.cardDao()

    @Singleton
    @Provides
    fun provideCardSetDao(database: AppDatabase) = database.cardSetDao()

    @Singleton
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

    @Singleton
    @CollectionData
    @Provides
    fun provideCollectionCardRepository(): CardRepository =
        CollectionCardRepository()
}