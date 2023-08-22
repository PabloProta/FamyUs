package com.famy.us.repository.model

/**
 * Data class that represents the Family member from repository.
 *
 * @property id the identifier for this member.
 * @property name the name of this member.
 * @property tasks the tasks assigned by it.
 * @property score the total score made by it.
 */
data class RepositoryFamilyMember(
    val id: Int,
    val name: String,
    val tasks: List<RepositoryTask> = emptyList(),
    val score: Int,
    val isAdmin: Boolean
)
