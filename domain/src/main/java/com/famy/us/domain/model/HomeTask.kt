package com.famy.us.domain.model

import java.util.Date

/**
 * Model that represents the homeTask.
 *
 * @property id is the task id.
 * @property name the name of the task.
 * @property description the task description.
 * @property position the position in the list.
 * @property point how many point it value.
 * @property isAssigned if this task was already assigned.
 */
data class HomeTask(
    val id: Int,
    val name: String,
    val description: String,
    val position: Int,
    val point: Int,
    val isAssigned: Boolean,
    val start: Date? = null,
    val finish: Date? = null,
) {
    companion object {
        /**
         * Variable to return a Empty object without set any attribute.
         */
        val Empty: HomeTask = HomeTask(
            id = 0,
            name = "",
            description = "",
            position = -1,
            point = 0,
            isAssigned = false,
            start = null,
            finish = null,
        )
    }
}
