package com.famy.us.domain.repository

import com.famy.us.domain.model.HomeTask
import kotlinx.coroutines.flow.Flow

/**
 * Interface for repository that will be implemented in the data module.
 * This interface is responsible to provide standard methods to the domain layer related to
 * the FamilyMember only.
 */
interface HomeTaskRepository {

    /**
     * Method to get all tasks saved.
     */
    suspend fun getAllTask(): Flow<List<HomeTask>>

    /**
     * Method to get a task by id.
     *
     * @param id is the Id used to get the task.
     */
    suspend fun getTaskById(id: Int): Flow<HomeTask>

    /**
     * Method to save a new task.
     *
     * @param newTask the task that will be saved.
     */
    suspend fun saveTask(newTask: HomeTask)

    /**
     * Method to update a existent task.
     *
     * @param task the taskt that will be updated.
     */
    suspend fun updateTask(task: HomeTask)

    /**
     * Method to delete a task by Id.
     *
     * @param id the Id used to identify the task that are going to be deleted.
     */
    suspend fun deleteTaskById(id: Int)
}
