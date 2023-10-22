package com.famy.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.famy.database.model.HomeTask
import kotlinx.coroutines.flow.Flow

/**
 * Interface responsible to provide the data access to the objects related to the family member.
 */
@Dao
interface HomeTaskDao {

    /**
     * Method to get all tasks.
     */
    @Query("SELECT * FROM home_task ORDER BY position ASC")
    fun getAllTasks(): Flow<List<HomeTask>>

    /**
     * Method to get a task by id.
     */
    @Query("SELECT * FROM home_task WHERE id = (:taskId)")
    fun getTaskById(taskId: Int): Flow<HomeTask>

    /**
     * Method to save a task base on the given object.
     */
    @Insert
    fun saveTask(newTask: HomeTask)

    /**
     * Method to update a task.
     */
    @Update
    fun updateTask(task: HomeTask)

    /**
     * Method to delete a task by Id.
     */
    @Query("DELETE FROM home_task WHERE id = (:taskId)")
    fun deleteTaskById(taskId: Int)
}
