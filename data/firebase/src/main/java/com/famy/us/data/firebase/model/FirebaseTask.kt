package com.famy.us.data.firebase.model

/**
 * Model representing how the family task data will be organized at the firebase realtime
 * database.
 *
 * @property name a name description about the task created.
 * @property score the score given to complete this task.
 */
data class FirebaseTask(
    val id: Int? = 0,
    val name: String? = "",
    val score: Int? = 0,
)
