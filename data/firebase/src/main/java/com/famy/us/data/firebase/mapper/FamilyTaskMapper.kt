package com.famy.us.data.firebase.mapper

import com.famy.us.data.firebase.model.FirebaseTask
import com.famy.us.repository.model.RepositoryTask

/**
 * Class to map models of Family task between firebase model and repository.
 */
internal class FamilyTaskMapper {

    /**
     * Method to convert to firebase model.
     */
    fun toFirebase(listRepositoryTask: List<RepositoryTask>) = listRepositoryTask.map {
        toFirebase(it)
    }

    private fun toFirebase(repositoryTask: RepositoryTask) = FirebaseTask(
        id = repositoryTask.id,
        name = repositoryTask.name,
        score = repositoryTask.point,
    )

    /**
     * Method to convert to repository model.
     */
    fun toRepository(listFirebaseTask: List<FirebaseTask>) = listFirebaseTask.map {
        toRepository(it)
    }

    private fun toRepository(firebaseTask: FirebaseTask) = RepositoryTask(
        id = firebaseTask.id ?: 0,
        name = firebaseTask.name ?: "",
        description = firebaseTask.description ?: "",
        position = 0,
        point = firebaseTask.score ?: 0,
        isAssigned = false,
        null,
        null,
    )
}
