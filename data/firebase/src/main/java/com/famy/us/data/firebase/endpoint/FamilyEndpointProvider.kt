package com.famy.us.data.firebase.endpoint

import com.famy.us.data.firebase.FamyUsDatabase
import com.famy.us.data.firebase.model.FirebaseMemberReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

internal abstract class FamilyEndpointProvider {

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    private val auth: FirebaseAuth = Firebase.auth

    private val membersDatabase = FamyUsDatabase.root.members()

    private val familyDatabase = FamyUsDatabase.root.families()

    /**
     * Method to get the familyMember reference.
     *
     * @param membersDatabase the members reference at the firebase database.
     * @param auth the firebase auth to get the user unique id.
     * @param onGetMember callback for when the member reference is caught.
     * @param onGetFail callback for when this method fail on get member reference.
     */
    final fun getFamilyMemberReference(
        onGetMember: (FirebaseMemberReference) -> Unit,
        onGetFail: () -> Unit = {},
    ) {
        val memberEndpoint = membersDatabase.child(auth.uid.toString())
        memberEndpoint.get()
            .addOnSuccessListener { reference ->
                val memberReference: FirebaseMemberReference? =
                    reference.getValue(FirebaseMemberReference::class.java)

                if (memberReference != null) {
                    onGetMember(memberReference)
                } else {
                    onGetFail()
                }
            }
            .addOnFailureListener {
                onGetFail()
            }
    }

    final fun getMembersReference(
        onGetFamily: () -> Unit,
        onGetFail: () -> Unit,
    ) {
        getFamilyMemberReference(
            onGetMember = { memberReference ->
                familyDatabase
                    .child(memberReference.familyKey.toString())
                    .child("members")
            },
        )
    }
}
