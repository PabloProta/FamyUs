package com.famy.us.domain.model

import java.util.Date

/**
 * Model that represents the homeTask.
 *
 * @property id is the task id.
 * @property name the name of the task.
 * @property point how many point it value.
 * @property isAssigned if this task was already assigned.
 */
data class HomeTask(
    val id: Int,
    val name: String,
    val point: Int,
    val isAssigned: Boolean,
    val start: Date? = null,
    val finish: Date? = null,
)
