package com.famy.us.core.extensions

import android.os.Build
import java.util.regex.Pattern

/**
 * Logger utility class.
 */
object Logger {

    /**
     * Check if this is a production build.
     */
    private val isProductBuild = Build.TYPE == "user"

    /**
     * Flag to be used for debug logs.
     */
    val DEBUG = !isProductBuild

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
    fun getTag(): String = if (DEBUG) {
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
