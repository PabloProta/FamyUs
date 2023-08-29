package com.famy.us.core.extensions

/**
 * Method to transform a byte array into a hexadecimal value
 */
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
