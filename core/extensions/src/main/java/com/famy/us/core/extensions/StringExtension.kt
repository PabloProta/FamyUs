package com.famy.us.core.extensions

import java.security.MessageDigest

/**
 * Method to make a string in a md5 byte array.
 */
fun String.toMD5(): ByteArray = MessageDigest.getInstance("MD5").digest(toByteArray())
