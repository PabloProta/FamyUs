package com.famy.us

/**
 * Class containing all intent possibles to be handled at Home screen.
 */
sealed class HomeIntent {

    /**
     * Intent to add a new task
     */
    object AddTask : HomeIntent()

    /**
     * Intent to edit a task
     */
    object EditTask : HomeIntent()

    /**
     * Intent to delete a task.
     */
    object DeleteTask : HomeIntent()

    /**
     * Intent to load all tasks.
     */
    object LoadTasks : HomeIntent()
}
