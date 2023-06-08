package com.famy.database.datasources

import com.famy.database.DatabaseProvider
import com.famy.database.mapper.FamilyMemberMapper
import com.famy.us.repository.datasource.FamilyMemberDataSource
import com.famy.us.repository.model.FamilyMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Class that implements the family member data source interface.
 *
 * @param databaseProvider a class that provide a database instance to us access the dao.
 * @property mapper a class tha map classes between repository and database module.
 */
internal class FamilyMemberDataSourceImpl(
    databaseProvider: DatabaseProvider,
    private val mapper: FamilyMemberMapper,
) : FamilyMemberDataSource {

    private val familyMemberDao = databaseProvider.database.familyMemberDao()

    override suspend fun getAllMembers(): Flow<List<FamilyMember>> = withContext(Dispatchers.IO) {
        familyMemberDao.getAllMembers()
            .distinctUntilChanged()
            .map { memberList ->
                memberList.map {
                    mapper.toRepository(it)
                }
            }
    }

    override suspend fun getMemberById(id: Int): Flow<FamilyMember> = withContext(Dispatchers.IO) {
        familyMemberDao.getMemberById(id)
            .distinctUntilChanged()
            .map { member ->
                mapper.toRepository(member)
            }
    }

    override suspend fun saveMember(familyMember: FamilyMember) {
        withContext(Dispatchers.IO) {
            familyMemberDao.saveMember(mapper.toDatabase(familyMember))
        }
    }

    override suspend fun updateMember(familyMember: FamilyMember) {
        withContext(Dispatchers.IO) {
            familyMemberDao.updateMember(mapper.toDatabase(familyMember))
        }
    }

    override suspend fun deleteMemberById(id: Int) {
        withContext(Dispatchers.IO) {
            familyMemberDao.deleteMemberById(id)
        }
    }
}
