package com.famy.us.repository.repository

import com.famy.us.domain.model.AuthenticationMethods
import com.famy.us.domain.model.FamilyMember
import com.famy.us.domain.repository.FamilyRepository
import com.famy.us.repository.datasource.FamilyMemberDataSource
import com.famy.us.repository.datasource.firebase.FamilyFirebaseDataSource
import com.famy.us.repository.mapper.FamilyMemberMapper
import com.famy.us.repository.model.AdminMember
import com.famy.us.repository.model.NonAdminMember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.famy.us.domain.model.AdminMember as DomainAdminMember
import com.famy.us.domain.model.NonAdminMember as DomainNonAdminMember

/**
 * Class that implements the repository interface at domain layer.
 *
 * @property dataSource is the class responsible for the data source for this repository
 * @property firebaseDataSource the data source based on the firebase.
 * @property familyMemberMapper is the mapper used by this repository to convert a model
 * between domain and data module.
 */
internal class FamilyRepositoryImpl(
    private val dataSource: FamilyMemberDataSource,
    private val firebaseDataSource: FamilyFirebaseDataSource,
    private val familyMemberMapper: FamilyMemberMapper,
) : FamilyRepository {

    override suspend fun getAllMembers(): Flow<List<FamilyMember>> =
        dataSource.getAllMembers()
            .map { memberList ->
                memberList.map {
                    if (it is AdminMember) {
                        familyMemberMapper.toDomain(it)
                    } else {
                        it as NonAdminMember
                        familyMemberMapper.toDomain(it)
                    }
                }
            }

    override suspend fun getMemberById(id: Int): Flow<FamilyMember> =
        dataSource.getMemberById(id)
            .map {
                if (it is AdminMember) {
                    familyMemberMapper.toDomain(it)
                } else {
                    it as NonAdminMember
                    familyMemberMapper.toDomain(it)
                }
            }

    override suspend fun saveMember(familyMember: FamilyMember) {
        if (familyMember is DomainAdminMember) {
            dataSource.saveMember(familyMemberMapper.toRepository(familyMember))
        } else {
            familyMember as DomainNonAdminMember
            dataSource.saveMember(familyMemberMapper.toRepository(familyMember))
        }
    }

    override suspend fun updateMember(familyMember: FamilyMember) {
        if (familyMember is DomainAdminMember) {
            dataSource.updateMember(familyMemberMapper.toRepository(familyMember))
        } else {
            familyMember as DomainNonAdminMember
            dataSource.updateMember(familyMemberMapper.toRepository(familyMember))
        }
    }

    override suspend fun deleteMemberById(id: Int) {
        dataSource.deleteMemberById(id)
    }

    override fun isUserAuthenticated(): Boolean = firebaseDataSource.isUserAuthenticated()
    override fun authenticateUser(
        methods: AuthenticationMethods,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        when (methods) {
            is AuthenticationMethods.Google -> firebaseDataSource.authenticateWithGoogle(
                methods.idToken,
                onSuccess = onSuccess,
                onFail = onFail,
            )

            else -> {
                // Do nothing...
            }
        }
    }

    override fun isUserAlreadyRegistered(): Boolean {
        return false
    }

    override fun setIsUserRegistered() {
        TODO("Not yet implemented")
    }
}
