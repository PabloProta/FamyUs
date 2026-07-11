package com.famy.us.core.extensions

import android.content.Context
import android.content.pm.ApplicationInfo
import java.util.regex.Pattern

/**
 * Logger utility class.
 */
object Logger {

    /**
     * Flag to be used for debug logs.
     */
    var isDebug: Boolean = false
        private set

    /**
     * Call this exactly once in your Application class onCreate()
     */
    fun init(context: Context) {
        val flags = context.applicationContext.applicationInfo.flags
        isDebug = (flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }

    /**
     * Tag Prefix to be used across the application.
     */
    private const val TAG_PREFIX = "FAMY."

    /**
     * Default tag.
     */
    private const val DEFAULT_TAG = "${TAG_PREFIX}FamyUs"

    /**
     * Within a stacktrace, our class appears in this position.
     */
    private const val CLASS_STACK_INDEX = 3

    /**
     * Regex to match anonymous class.
     */
    private val ANONYMOUS_CLASS_PATTERN = Pattern.compile("(\\$\\d+)+$")

    /**
     * Returns a TAG for the caller class. Tag format is : MM.classname
     *
     * @return The Tag to be used for logging.
     */
    fun getTag(): String = if (isDebug) {
        var tag = Thread.currentThread().stackTrace[CLASS_STACK_INDEX].className
        val matcher = ANONYMOUS_CLASS_PATTERN.matcher(tag)

        if (matcher.find()) {
            tag = matcher.replaceAll("")
        }

        TAG_PREFIX + tag.substring(tag.lastIndexOf('.') + 1)
    } else {
        DEFAULT_TAG
    }
}
