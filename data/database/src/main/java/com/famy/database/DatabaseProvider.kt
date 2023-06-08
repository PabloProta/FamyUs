package com.famy.database

import android.content.Context
import androidx.room.Room

/**
 * Provide a Database applications instance by Room.
 *
 * @property context the context used to build the database.
 */
internal class DatabaseProvider(private val context: Context) {

    /**
     * The instance of the RoomDatabase.
     */
    internal val database: AppDatabase = buildDatabase()

    private fun buildDatabase(): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME,
    ).build()

    private companion object {
        private const val DATABASE_NAME = "famy-us-database"
    }
}
