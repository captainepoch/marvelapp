package com.adolfo.core.logging

import timber.log.Timber

class HyperlinkDebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        with(element) {
            return "($fileName:$lineNumber) $methodName()"
        }
    }
}
