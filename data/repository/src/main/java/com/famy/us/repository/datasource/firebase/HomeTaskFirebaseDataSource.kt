package com.famy.us.repository.datasource.firebase

import com.famy.us.repository.model.RepositoryTask
import kotlinx.coroutines.flow.Flow

/**
 * Datasource interface for the firebase datasource.
 */
interface HomeTaskFirebaseDataSource {

    /**
     * Method to create an task.
     *
     * @param task the task that will be created.
     */
    fun createTask(task: RepositoryTask)

    /**
     * Method to get all tasks.
     *
     * @return list of tasks related to entire family
     */
    fun geAllTask(): Flow<List<RepositoryTask>>

    /**
     * Method to get
     */
    fun getTaskByMemberId(memberId: String): Flow<List<RepositoryTask>>

    fun getTaskById(taskId: String): Flow<RepositoryTask>
}
