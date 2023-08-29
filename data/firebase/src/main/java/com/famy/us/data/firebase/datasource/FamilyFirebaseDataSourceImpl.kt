package com.famy.us.data.firebase.datasource

import com.famy.us.core.extensions.logD
import com.famy.us.core.extensions.logW
import com.famy.us.core.extensions.toHex
import com.famy.us.core.extensions.toMD5
import com.famy.us.data.firebase.FamyUsDatabase
import com.famy.us.data.firebase.mapper.FamilyMemberMapper
import com.famy.us.data.firebase.model.FirebaseMember
import com.famy.us.data.firebase.model.FirebaseMemberReference
import com.famy.us.repository.datasource.FamilyFirebaseDataSource
import com.famy.us.repository.model.RepositoryFamilyMember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Class that implements the respective data source.
 */
internal class FamilyFirebaseDataSourceImpl(
    private val familyMemberMapper: FamilyMemberMapper,
) : FamilyFirebaseDataSource {

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    private val auth: FirebaseAuth = Firebase.auth

    private val familyDatabase = FamyUsDatabase.root.families()

    private val memberDatabase = FamyUsDatabase.root.members()

    override fun isUserAuthenticated(): Boolean = auth.currentUser != null

    override fun authenticateWithGoogle(
        idToken: String,
        onSuccess: () -> Unit,
        onFail: () -> Unit,
    ) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    logD { "Logged with success" }
                    onSuccess()
                } else {
                    logD { "Login failed" }
                    onFail()
                }
            }
    }

    override fun createNewFamily(familyName: String, newMember: RepositoryFamilyMember) {
        val firebaseMember = familyMemberMapper.toFirebase(newMember)
        val familyPush = familyDatabase.push()
        val familyKey = familyPush.key
        val familyIdentifier = familyKey?.toMD5()?.toHex()
        familyPush.child("name").setValue(familyName)
        familyPush.child("identifier").setValue(familyIdentifier)
        familyPush.child("members").child(auth.uid.toString()).setValue(firebaseMember)
        memberDatabase.child(auth.uid.toString()).setValue(
            FirebaseMemberReference(
                familyKey = familyKey,
            ),
        )
    }

    override fun getCurrentMember(): Flow<RepositoryFamilyMember?> = callbackFlow {
        var memberGotcha: FirebaseMember? = null
        val memberEndpoint = memberDatabase.child(auth.uid.toString())
        memberEndpoint.get()
            .addOnSuccessListener { reference ->
                val memberReference: FirebaseMemberReference? =
                    reference.getValue(FirebaseMemberReference::class.java)
                logD { "member found! family: $memberReference" }
                familyDatabase
                    .child(memberReference?.familyKey.toString())
                    .child("members")
                    .child(auth.uid.toString())
                    .get()
                    .addOnSuccessListener { member ->
                        memberGotcha = member.getValue(FirebaseMember::class.java)
                        logD { "member caught $memberGotcha" }
                        memberGotcha?.let {
                            trySend(familyMemberMapper.toRepository(it))
                        } ?: trySend(null)
                    }.addOnFailureListener {
                        logW { "Failed on get member" }
                        trySend(null)
                    }
            }
            .addOnFailureListener {
                logW { "Fail on search member" }
                trySend(null)
            }
        awaitClose {
            logD { "Current member is: $memberGotcha" }
        }
    }

    override fun getFamilyInvite(): Flow<String> = callbackFlow {
        val memberEndpoint = memberDatabase.child(auth.uid.toString())
        memberEndpoint.get()
            .addOnSuccessListener { reference ->
                val memberReference: FirebaseMemberReference? =
                    reference.getValue(FirebaseMemberReference::class.java)
                familyDatabase
                    .child(memberReference?.familyKey.toString())
                    .child("identifier")
                    .get()
                    .addOnSuccessListener { identifier ->
                        identifier.getValue(String::class.java)?.let {
                            trySend(it)
                        } ?: trySend("")
                    }
            }
            .addOnFailureListener {
                logW { "Fail on search member" }
                trySend("")
            }

        awaitClose()
    }
}
