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

@Database(
    entities = [CardDbEntity::class, CardSetDbEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    abstract fun cardSetDao(): CardSetDao

    companion object {

        private var firstTimeDatabaseCreated = false

        private var onDbOpened: (Boolean) -> Unit = {}

        private const val DATABASE_NAME = "app_database"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).addCallback(getOnDatabaseCreateListener()).build()
        }

        private fun getOnDatabaseCreateListener() =
            object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    firstTimeDatabaseCreated = true
                    super.onCreate(db)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    onDbOpened(firstTimeDatabaseCreated)
                }
            }

        fun setOnDatabaseOpenListener(
            onDbOpened: (firstTimeDatabaseCreated: Boolean) -> Unit
        ) {
            this.onDbOpened = onDbOpened
        }
    }
}
