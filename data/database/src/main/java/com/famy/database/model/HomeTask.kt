package com.famy.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Model that represents the homeTask.
 * @property id is the task id.
 * @property name the name of the task.
 * @property point how many point it value.
 * @property isAssigned if this task was already assigned.
 */
@Entity(tableName = "home_task")
data class HomeTask(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "point") val point: Int,
    @ColumnInfo(name = "is_assigned") val isAssigned: Boolean,
    @ColumnInfo(name = "start") val start: Date?,
    @ColumnInfo(name = "finish") val finish: Date?,
)
