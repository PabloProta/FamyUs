package com.famy.database.mapper

import com.famy.database.model.HomeTask as DatabaseHomeTask
import com.famy.us.repository.model.RepositoryTask as RepositoryHomeTask

/**
 * Mapper class to convert models between data:database and data:repository module.
 */
internal class HomeTaskMapper {

    /**
     * Method to convert a database home task into a repository home task data model.
     *
     * @param databaseHomeTask the model that will be converted.
     * @return a new model converted to the repository module.
     */
    fun toRepository(databaseHomeTask: DatabaseHomeTask): RepositoryHomeTask =
        databaseHomeTask.run {
            RepositoryHomeTask(
                id,
                name,
                description,
                position,
                point,
                isAssigned,
                start,
                finish,
            )
        }

    /**
     * Method to convert a list of database home task model into a list of repository data model.
     *
     * @param listDatabaseHomeTask is a list of home task from database.
     * @return a list of repository data model.
     */
    fun toRepository(listDatabaseHomeTask: List<DatabaseHomeTask>): List<RepositoryHomeTask> =
        listDatabaseHomeTask.map {
            toRepository(it)
        }

    /**
     * Method to convert a repository home task into a database home task data model.
     *
     * @param repositoryHomeTask the model that will be converted.
     * @return a new model converted to the database module.
     */
    fun toDatabase(repositoryHomeTask: RepositoryHomeTask): DatabaseHomeTask =
        repositoryHomeTask.run {
            DatabaseHomeTask(
                id,
                name,
                description,
                position,
                point,
                isAssigned,
                start,
                finish,
            )
        }

    /**
     * Method to convert a list of repository home task model into a list of database data model.
     *
     * @param listRepositoryHomeTask is a list of home task from database.
     * @return a list of repository data model.
     */
    fun toDatabase(listRepositoryHomeTask: List<RepositoryHomeTask>): List<DatabaseHomeTask> =
        listRepositoryHomeTask.map {
            toDatabase(it)
        }
}
