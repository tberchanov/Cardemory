package com.cardemory.carddata.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cardemory.carddata.data.db.card.CardDao
import com.cardemory.carddata.data.db.card.CardDbEntity
import com.cardemory.carddata.data.db.cardset.CardSetDao
import com.cardemory.carddata.data.db.cardset.CardSetDbEntity
import com.cardemory.carddata.interactor.PrepopulateDbInteractor
import timber.log.Timber

@Database(
    entities = [CardDbEntity::class, CardSetDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun cardSetDao(): CardSetDao

    companion object {

        private const val DATABASE_NAME = "app_database"

        private lateinit var appDatabase: AppDatabase

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (!::appDatabase.isInitialized) {
                appDatabase = buildDatabase(context)
            }
            return appDatabase
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(getOnDatabaseCreateListener(context)).build()
        }

        private fun getOnDatabaseCreateListener(context: Context) =
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    PrepopulateDbInteractor(context, getInstance(context))(Unit) { either ->
                        either.either(
                            { Timber.e("prepopulateDbInteractor failure: $it") },
                            { Timber.d("prepopulateDbInteractor success: $it") }
                        )
                    }
                }
            }
    }
}
