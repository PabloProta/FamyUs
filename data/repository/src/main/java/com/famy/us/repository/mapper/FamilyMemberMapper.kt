package com.famy.us.repository.mapper

import com.famy.us.domain.model.AdminMember as DomainAdminMember
import com.famy.us.domain.model.NonAdminMember as DomainNonAdminMember
import com.famy.us.repository.model.AdminMember as DataAdminMember
import com.famy.us.repository.model.NonAdminMember as DataNonAdminMember

/**
 * Class to map the models of FamilyMember between modules.
 */
internal class FamilyMemberMapper(private val homeTaskMapper: HomeTaskMapper) {

    /**
     * Method to convert a data model to domain module.
     *
     * @param dataAdminMember the model that will be converted to the domain module.
     * @return a model already converted to the domain module.
     */
    fun toDomain(dataAdminMember: DataAdminMember): DomainAdminMember = dataAdminMember.run {
        DomainAdminMember(
            id,
            name,
            homeTaskMapper.toDomain(tasks),
            score,
        )
    }

    /**
     * Method to convert a data model to domain module.
     *
     * @param dataNonAdminMember the model that will be converted to the domain module.
     * @return a model already converted to the domain module.
     */
    fun toDomain(dataNonAdminMember: DataNonAdminMember): DomainNonAdminMember =
        dataNonAdminMember.run {
            DomainNonAdminMember(
                id,
                name,
                homeTaskMapper.toDomain(tasks),
                score,
            )
        }

    /**
     * Method to convert a domain model to data module.
     *
     * @param domainAdminMember the model that will be converted to the data module.
     * @return a model already converted to the data module.
     */
    fun toRepository(domainAdminMember: DomainAdminMember): DataAdminMember =
        domainAdminMember.run {
            DataAdminMember(
                id,
                name,
                homeTaskMapper.toRepository(tasks),
                score,
            )
        }

    /**
     * Method to convert a domain model to data module.
     *
     * @param domainNonAdminMember the model that will be converted to the data module.
     * @return a model already converted to the data module.
     */
    fun toRepository(domainNonAdminMember: DomainNonAdminMember): DataNonAdminMember =
        domainNonAdminMember.run {
            DataNonAdminMember(
                id,
                name,
                homeTaskMapper.toRepository(tasks),
                score,
            )
        }
}
