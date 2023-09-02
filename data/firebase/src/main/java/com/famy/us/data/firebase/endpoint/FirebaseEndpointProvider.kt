package com.famy.us.data.firebase.endpoint

import com.famy.us.data.firebase.FamyUsDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

internal class FirebaseEndpointProvider : FamilyEndpointProvider() {

    /**
     * This is the firebase auth entry point for the
     * authentication screen.
     */
    private val auth: FirebaseAuth = Firebase.auth

    private val familyDatabase = FamyUsDatabase.root.families()

    private val membersDatabase = FamyUsDatabase.root.members()
}
