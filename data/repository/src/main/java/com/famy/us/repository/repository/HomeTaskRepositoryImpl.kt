package com.famy.us.repository.repository

import com.famy.us.domain.model.HomeTask
import com.famy.us.domain.repository.HomeTaskRepository
import com.famy.us.repository.datasource.HomeTaskDataSource
import com.famy.us.repository.mapper.HomeTaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Class that implements the repository interface at domain layer.
 *
 * @property dataSource is the class responsible for the data source for this repository
 * @property mapper is the mapper used by this repository to convert a model
 * between domain and data module.
 */
internal class HomeTaskRepositoryImpl(
    private val dataSource: HomeTaskDataSource,
    private val mapper: HomeTaskMapper,
) : HomeTaskRepository {

    override suspend fun getAllTask(): Flow<List<HomeTask>> = dataSource.getAllTask()
        .map { taskList ->
            taskList.map {
                mapper.toDomain(it)
            }
        }

    override suspend fun getTaskById(id: Int): Flow<HomeTask> = dataSource.getTaskById(id)
        .map {
            mapper.toDomain(it)
        }

    override suspend fun saveTask(newTask: HomeTask) {
        dataSource.saveTask(mapper.toRepository(newTask))
    }

    override suspend fun updateTask(task: HomeTask) {
        dataSource.updateTask(mapper.toRepository(task))
    }

    override suspend fun deleteTaskById(id: Int) {
        dataSource.deleteTaskById(id)
    }
}
