package com.adolfo.core.extensions

import org.junit.Test

class BooleanExtTest {

    @Test
    fun `should be null and empty value boolean`() {
        val expectedValue = false
        val booleanValue: Boolean? = null
        val testBoolean = booleanValue.orEmpty()

        assert(expectedValue == testBoolean)
    }

    @Test
    fun `should be boolean value itself`() {
        val expectedValue = true
        val testBoolean = expectedValue.orEmpty()

        assert(expectedValue == testBoolean)
    }
}
