package com.famy.us.data.firebase.model

/**
 * Model representing how the family member will be organized at the firebase realtime database.
 *
 * @property id the user id to identify it in the family.
 * @property name the member name.
 * @property tasks a list of tasks' id.
 * @property score the member score.
 * @property isAdmin if user is admin.
 */
data class FirebaseMember(
    val id: Int? = 0,
    val name: String? = "",
    val tasks: List<Int>? = emptyList(),
    val score: Int? = 0,
    val isAdmin: Boolean? = false,
)
