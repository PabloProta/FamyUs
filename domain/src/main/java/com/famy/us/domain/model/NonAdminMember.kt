package com.famy.us.domain.model

/**
 * A class that represents the NonAdmin Member.
 *
 * @property id the identifier for this member.
 * @property name the name of this member.
 * @property tasks the tasks assigned by it.
 * @property score the total score made by it.
 */
data class NonAdminMember(
    val id: Int,
    val name: String,
    val tasks: List<HomeTask>,
    val score: Int,
) : FamilyMember(id, name, tasks, score)
