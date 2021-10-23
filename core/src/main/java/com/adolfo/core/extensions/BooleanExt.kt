package com.adolfo.core.extensions

fun Boolean?.orEmpty(): Boolean {
    return this ?: false
}
