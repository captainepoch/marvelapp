package com.adolfo.core.extensions

import com.adolfo.core.platform.Constants
import java.math.BigInteger
import java.security.MessageDigest

val String.Companion.Empty
    inline get() = ""

fun String?.isNull(): Boolean {
    return (this == null)
}

fun String.isEmptyOrBlank(): Boolean {
    return (this.isEmpty() || this.isBlank())
}

fun String.md5(): String {
    val md = MessageDigest.getInstance(Constants.CryptographyConstants.MD5)
    return BigInteger(1, md.digest(this.toByteArray(Charsets.UTF_8)))
        .toString(16)
        .padStart(32, '0')
}
