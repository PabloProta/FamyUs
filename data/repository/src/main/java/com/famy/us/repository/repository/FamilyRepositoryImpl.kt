package com.famy.us.repository.repository

import com.famy.us.domain.model.AdminMember
import com.famy.us.domain.model.AuthenticationMethods
import com.famy.us.domain.model.FamilyMember
import com.famy.us.domain.repository.FamilyRepository
import com.famy.us.repository.datasource.firebase.FamilyFirebaseDataSource
import com.famy.us.repository.mapper.FamilyMemberMapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Class that implements the repository interface at domain layer.
 *
 * @property firebaseDataSource the data source based on the firebase.
 * @property familyMemberMapper the mapper for the family members.
 */
internal class FamilyRepositoryImpl(
    private val firebaseDataSource: FamilyFirebaseDataSource,
    private val familyMemberMapper: FamilyMemberMapper,
) : FamilyRepository {

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

    override fun createNewFamily(familyName: String, newMember: AdminMember) {
        val member = familyMemberMapper.toRepository(newMember)
        firebaseDataSource.createNewFamily(familyName, member)
    }

    override fun getCurrentMember(): Flow<FamilyMember?> = callbackFlow {
        firebaseDataSource.getCurrentMember().collect {
            it?.let {
                trySend(familyMemberMapper.toDomain(it))
            } ?: trySend(null)
        }
        awaitClose()
    }
}
