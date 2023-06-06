package com.famy.us.repository.datasource

import com.famy.us.domain.model.HomeTask

/**
 * Interface responsible to provide methods to get from data source information and update them
 * about Home task.
 */
interface HomeTaskDataSource {

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
