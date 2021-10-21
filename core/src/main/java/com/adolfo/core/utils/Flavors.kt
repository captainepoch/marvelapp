package com.adolfo.core.utils

enum class Flavors(private val flavor: String) {
    PRE("pre"),
    PRO("pro");

    companion object {

        fun getFlavor(flavor: String) {
            when (flavor) {
                PRO.flavor -> PRO
                else -> PRE
            }
        }
    }
}
