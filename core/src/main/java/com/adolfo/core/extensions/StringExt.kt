package com.adolfo.core.extensions

import com.adolfo.core.platform.Constants
import java.math.BigInteger
import java.security.MessageDigest

fun String.Companion.empty() = ""

fun String.md5(): String {
    val md = MessageDigest.getInstance(Constants.CryptographyConstants.MD5)
    return BigInteger(1, md.digest(this.toByteArray(Charsets.UTF_8)))
        .toString(16)
        .padStart(32, '0')
}
