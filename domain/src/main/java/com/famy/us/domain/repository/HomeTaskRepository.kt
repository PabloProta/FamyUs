package com.famy.us.domain.repository

import com.famy.us.domain.model.HomeTask

/**
 * Interface for repository that will be implemented in the data module.
 * This interface is responsible to provide standard methods to the domain layer related to
 * the FamilyMember only.
 */
interface HomeTaskRepository {

    /**
     * Method to get a task by id.
     *
     * @param id is the Id used to get the task.
     */
    fun getTaskById(id: Int): HomeTask

    /**
     * Method to save a new task.
     *
     * @param newTask the task that will be saved.
     */
    fun saveTask(newTask: HomeTask)

    /**
     * Method to update a existent task.
     *
     * @param task the taskt that will be updated.
     */
    fun updateTask(task: HomeTask)

    /**
     * Method to delete a task by Id.
     *
     * @param id the Id used to identify the task that are going to be deleted.
     */
    fun deleteTaskById(id: Int)
}
