package com.famy.us.repository.mapper

import com.famy.us.domain.model.HomeTask as DomainHomeTask
import com.famy.us.repository.model.HomeTask as DataHomeTask

/**
 * Class to map the models of HomeTaskMapper between modules.
 */
internal class HomeTaskMapper {

    /**
     * Method to convert da data model to domain model.
     *
     * @param dataHomeTask is the model from data module.[
     * @return a domain model converted.
     */
    fun toDomain(dataHomeTask: DataHomeTask): DomainHomeTask = dataHomeTask.run {
        DomainHomeTask(
            id,
            name,
            point,
            isAssigned,
            start,
            finish
        )
    }

    /**
     * Method to convert a entire list data model to domain list of the given model.
     *
     * @param listDataHomeTask a list of data model.
     * @return a list of the model converted to domain module.
     */
    fun toDomain(listDataHomeTask: List<DataHomeTask>): List<DomainHomeTask> =
        listDataHomeTask.map {
            toDomain(it)
        }

    /**
     * Method to convert a domain model to data model.
     *
     * @param domainHomeTask is the model from domain module.
     * @return a data model converted.
     */
    fun toRepository(domainHomeTask: DomainHomeTask): DataHomeTask = domainHomeTask.run {
        DataHomeTask(
            id,
            name,
            point,
            isAssigned,
            start,
            finish
        )
    }

    /**
     * Method to convert a entire list domain model to data list of the given model.
     *
     * @param listDomainHomeTask a list of domain model.
     * @return a list of the model converted to data module.
     */
    fun toRepository(listDomainHomeTask: List<DomainHomeTask>): List<DataHomeTask> =
        listDomainHomeTask.map {
            toRepository(it)
        }
}
