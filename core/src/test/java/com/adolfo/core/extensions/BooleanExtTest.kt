package com.adolfo.core.extensions

import org.junit.Test

class BooleanExtTest {

    @Test
    fun `should be null and false value boolean`() {
        val expectedValue = false
        val booleanValue: Boolean? = null
        val testBoolean = booleanValue.orFalse()

        assert(expectedValue == testBoolean)
    }

    @Test
    fun `should be bool asigned value itself`() {
        val expectedValue = true
        val testBoolean = expectedValue.orFalse()

        assert(expectedValue == testBoolean)
    }
}
