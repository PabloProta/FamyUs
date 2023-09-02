package com.famy.us.repository.model

import java.util.Date

/**
 * Model that represents the homeTask.
 * @property id is the task id.
 * @property name the name of the task.
 * @property description the task description,
 * @property point how many point it value.
 * @property isAssigned if this task was already assigned.
 */
data class RepositoryTask(
    val id: Int,
    val name: String,
    val description: String,
    val point: Int,
    val isAssigned: Boolean,
    val start: Date?,
    val finish: Date?,
)
