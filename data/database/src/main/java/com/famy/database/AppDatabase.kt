package com.famy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.famy.database.dao.FamilyMemberDao
import com.famy.database.dao.HomeTaskDao
import com.famy.database.model.FamilyMember
import com.famy.database.model.HomeTask

/**
 *  Class to instantiate the Room database.
 *
 */
@Database(entities = [FamilyMember::class, HomeTask::class], version = 1)
@TypeConverters(DatabaseConverters::class)
internal abstract class AppDatabase : RoomDatabase() {

    /**
     * Method to provide the data access object related to the family member.
     */
    abstract fun familyMemberDao(): FamilyMemberDao

    /**
     * Method to provide the data access object related to the home task.
     */
    abstract fun homeTaskDao(): HomeTaskDao
}
