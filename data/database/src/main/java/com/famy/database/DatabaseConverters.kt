package com.famy.database

import androidx.room.TypeConverter
import com.famy.database.model.HomeTask
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Class to converter the type between room and application.
 */
class DatabaseConverters {

    /**
     * Convert a timestamp to a Date object.
     *
     * @param value the timestamp.
     * @return a [Date] object with the given timestamp.
     */
    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let { Date(it) }

    /**
     * Method to convert a [Date] object into a timestamp.
     *
     * @param date is the [Date] that will be converted.
     * @return a [Long] representing the timestamp.
     */
    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time

    /**
     * Convert a List of [HomeTask] into a Json string.
     *
     * @param tasks the tasks that will be converted into json.
     * @return a json string.
     */
    @TypeConverter
    fun tasksToJson(tasks: List<HomeTask>): String = Gson().toJson(tasks)

    /**
     * Method to convert a json string into [List] of [HomeTask].
     *
     * @param json the String that represents the list of hometask.
     * @return the [List] of [HomeTask] based on the json.
     */
    @TypeConverter
    fun jsonToTasks(json: String): List<HomeTask> {
        val listType = object : TypeToken<List<HomeTask>>() {}.type

        return Gson().fromJson(json, listType)
    }
}
