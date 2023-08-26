package com.adolfo.core.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}
