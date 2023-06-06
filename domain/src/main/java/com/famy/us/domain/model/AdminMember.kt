package com.famy.us.domain.model

/**
 * Data class that represents the Admin member.
 *
 * @property id the identifier for this member.
 * @property name the name of this member.
 * @property tasks the tasks assigned by it.
 * @property score the total score made by it.
 */
data class AdminMember(
    val id: Int,
    val name: String,
    val tasks: List<HomeTask>,
    val score: Int,
) : FamilyMember(id, name, tasks, score)
