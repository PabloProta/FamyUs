package com.famy.us.core.extensions

/**
 * Method to switch position between to elements in the list.
 *
 * @param from the element that will be replace another one.
 * @param to the element that will switch with [from].
 */
fun <T> List<T>.move(from: Int, to: Int): List<T> =
    if (this.isEmpty() || from == to) {
        this
    } else {
        val current = this.toMutableList()
        current.removeAt(from)?.run {
            current.add(to, this)
        }
        current
    }
