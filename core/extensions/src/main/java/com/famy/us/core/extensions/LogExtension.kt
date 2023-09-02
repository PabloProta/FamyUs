package com.famy.us.core.extensions

import android.util.Log

/**
 * Inline function for the standard [Log.DEBUG] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param desc The message to be logged
 */
inline fun logD(tag: String = Logger.getTag(), desc: () -> String) {
    if (Logger.DEBUG) {
        Log.d(tag, desc())
    }
}

/**
 * Inline function for the standard [Log.VERBOSE] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param desc The message to be logged
 */
inline fun logV(tag: String = Logger.getTag(), desc: () -> String) {
    if (Logger.DEBUG) {
        Log.v(tag, desc())
    }
}

/**
 * Inline function for the standard [Log.ERROR] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param desc The message to be logged
 */
inline fun logE(tag: String = Logger.getTag(), desc: () -> String) {
    Log.e(tag, desc())
}

/**
 * Inline function for the standard [Log.ERROR] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param tr Throwable
 */
fun logE(tag: String = Logger.getTag(), tr: Throwable) {
    Log.e(tag, tr.localizedMessage, tr)
}

/**
 * Inline function for the standard [Log.WARN] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param desc The message to be logged
 * @param tr The [Throwable] to be logged
 */
inline fun logW(tag: String = Logger.getTag(), tr: Throwable? = null, desc: () -> String) {
    if (Logger.DEBUG) {
        tr?.let { Log.w(tag, desc(), tr) } ?: Log.w(tag, desc())
    }
}

/**
 * Inline function for the standard [Log.ERROR] message log.
 *
 * @param tag Used to identify the source of the log message
 * @param desc The message to be logged
 * @param tr The [Throwable] to be logged
 */
inline fun logE(tag: String = Logger.getTag(), tr: Throwable?, desc: () -> String) {
    tr?.let { Log.e(tag, desc(), tr) } ?: Log.e(tag, desc())
}

/**
 * Inline function to print logs even in user builds to help
 * debugging production issues.
 *
 * @param desc The message to be logged
 */
@SuppressWarnings("LogConditional")
inline fun logI(tag: String = Logger.getTag(), desc: () -> String) {
    Log.i(tag, desc())
}
