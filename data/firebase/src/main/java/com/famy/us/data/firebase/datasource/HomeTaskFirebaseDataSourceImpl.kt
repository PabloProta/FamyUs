package com.famy.us.data.firebase.datasource

import com.famy.us.data.firebase.FamyUsDatabase
import com.famy.us.data.firebase.model.FirebaseMemberReference
import com.famy.us.repository.datasource.firebase.HomeTaskFirebaseDataSource
import com.famy.us.repository.model.RepositoryTask
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of all method related to the home task that could be performed at the firebase.
 */
internal class HomeTaskFirebaseDataSourceImpl : HomeTaskFirebaseDataSource {

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    private val auth: FirebaseAuth = Firebase.auth

    private val familyDatabase = FamyUsDatabase.root.families()

    private val membersDatabase = FamyUsDatabase.root.members()

    override fun createTask(task: RepositoryTask) {
        getMemberFamilyEndpoint().get()
            .addOnSuccessListener { reference ->
                val memberReference: FirebaseMemberReference? =
                    reference.getValue(FirebaseMemberReference::class.java)
            }
    }

    override fun geAllTask(): Flow<List<RepositoryTask>> {
        TODO("Not yet implemented")
    }

    override fun getTaskByMemberId(memberId: String): Flow<List<RepositoryTask>> {
        TODO("Not yet implemented")
    }

    override fun getTaskById(taskId: String): Flow<RepositoryTask> {
        TODO("Not yet implemented")
    }

    private fun getMemberFamilyEndpoint(): DatabaseReference =
        membersDatabase.child(auth.uid.toString())
}
