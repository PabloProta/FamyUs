package com.famy.database.datasources

import com.famy.database.DatabaseProvider
import com.famy.database.mapper.HomeTaskMapper
import com.famy.us.repository.datasource.HomeTaskDataSource
import com.famy.us.repository.model.RepositoryTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Class that implements the home task data source interface.
 *
 * @param databaseProvider a class that provide a database instance to us access the dao.
 * @property mapper a class tha map classes between repository and database module.
 */
internal class HomeTaskDataSourceImpl(
    databaseProvider: DatabaseProvider,
    private val mapper: HomeTaskMapper,
) : HomeTaskDataSource {

    private val homeTaskDao = databaseProvider.database.homeTaskDao()

    override suspend fun getAllTask(): Flow<List<RepositoryTask>> = withContext(Dispatchers.IO) {
        homeTaskDao.getAllTasks().distinctUntilChanged().map {
            mapper.toRepository(it)
        }
    }

    override suspend fun getTaskById(id: Int): Flow<RepositoryTask> = withContext(Dispatchers.IO) {
        homeTaskDao.getTaskById(id).distinctUntilChanged().map {
            mapper.toRepository(it)
        }
    }

    override suspend fun saveTask(newTask: RepositoryTask) {
        withContext(Dispatchers.IO) {
            homeTaskDao.saveTask(mapper.toDatabase(newTask))
        }
    }

    override suspend fun updateTask(task: RepositoryTask) {
        withContext(Dispatchers.IO) {
            homeTaskDao.updateTask(mapper.toDatabase(task))
        }
    }

    override suspend fun deleteTaskById(id: Int) {
        withContext(Dispatchers.IO) {
            homeTaskDao.deleteTaskById(id)
        }
    }
}
