package com.famy.us.data.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class that provides the hierarchy instances of the famy us database at the firebase realtime
 * database.
 */
internal object FamyUsDatabase {
    private val firebaseDatabase: DatabaseReference
        get() = Firebase.database.reference

    /**
     * The root reference.
     */
    val root = Root()

    /**
     * Method to get the Root reference from database.
     */
    class Root(val members: Members = Members(), val families: Families = Families()) {

        /**
         * Invoke this class returning the reference.
         */
        operator fun invoke() = firebaseDatabase
    }

    /**
     * Class that provide the member reference in the database.
     */
    class Members {

        /**
         * Method that retrieve the member reference.
         */
        operator fun invoke() = root.invoke().child(REFERENCE)

        companion object {
            private const val REFERENCE = "members"
        }
    }

    /**
     * Class that provide the families reference in the database.
     */
    class Families {

        /**
         * Method that retrieve the families reference.
         */
        operator fun invoke() = firebaseDatabase.child(REFERENCE)

        companion object {
            private const val REFERENCE = "families"
        }
    }
}
