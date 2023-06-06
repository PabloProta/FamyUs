package com.famy.us.repository.model

/**
 * Model that represents the Family Member that will
 * make the tasks.
 *
 * @param id is the Identifier for the Member.
 * @param name is the name of the member.
 * @param tasks the task that was assigned by it.
 * @param score the total score sum all task assigned.
 */
abstract class FamilyMember(
    id: Int,
    name: String,
    tasks: List<HomeTask>,
    score: Int,
)
