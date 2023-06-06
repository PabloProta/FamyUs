package com.famy.us.repository.repository

import com.famy.us.domain.model.FamilyMember
import com.famy.us.domain.repository.FamilyRepository
import com.famy.us.repository.datasource.FamilyMemberDataSource
import com.famy.us.repository.mapper.FamilyMemberMapper
import com.famy.us.repository.model.AdminMember
import com.famy.us.repository.model.NonAdminMember
import com.famy.us.domain.model.AdminMember as DomainAdminMember
import com.famy.us.domain.model.NonAdminMember as DomainNonAdminMember

/**
 * Class that implements the repository interface at domain layer.
 *
 * @property dataSource is the class responsible for the data source for this repository
 * @property familyMemberMapper is the mapper used by this repository to convert a model
 * between domain and data module.
 */
internal class FamilyRepositoryImpl(
    private val dataSource: FamilyMemberDataSource,
    private val familyMemberMapper: FamilyMemberMapper,
) : FamilyRepository {

    override fun getAllMembers(): List<FamilyMember> = dataSource.getAllMembers().map { member ->
        if (member is AdminMember) {
            familyMemberMapper.toDomain(member)
        } else {
            familyMemberMapper.toDomain(member as NonAdminMember)
        }
    }

    override fun getMemberById(id: Int): FamilyMember {
        val member = dataSource.getMemberById(id)
        return if (member is AdminMember) {
            familyMemberMapper.toDomain(member)
        } else {
            familyMemberMapper.toDomain(member as NonAdminMember)
        }
    }

    override fun saveMember(familyMember: FamilyMember) {
        if (familyMember is DomainAdminMember) {
            dataSource.saveMember(familyMemberMapper.toRepository(familyMember))
        } else {
            dataSource.saveMember(
                familyMemberMapper.toRepository(
                    familyMember as DomainNonAdminMember,
                ),
            )
        }
    }

    override fun updateMember(familyMember: FamilyMember) {
        if (familyMember is DomainAdminMember) {
            dataSource.updateMember(familyMemberMapper.toRepository(familyMember))
        } else {
            dataSource.updateMember(
                familyMemberMapper.toRepository(
                    familyMember as DomainNonAdminMember,
                ),
            )
        }
    }

    override fun deleteMemberById(id: Int) = dataSource.deleteMemberById(id)
}
