package com.adolfo.core.extensions

fun <T> List<T>?.isNotNullOrEmpty(): Boolean {
    return (this != null && this.isNotEmpty())
}
