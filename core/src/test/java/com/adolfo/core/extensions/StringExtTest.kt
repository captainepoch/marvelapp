package com.adolfo.core.extensions

import java.util.Locale
import org.junit.Before
import org.junit.Test

class StringExtTest {

    @Before
    fun setUp() {
        Locale.setDefault(Locale("en", "IE"))
    }

    @Test
    fun `should be empty string`() {
        val expected = ""
        val test = String.Empty

        assert(expected == test)
    }

    @Test
    fun `should be not empty string`() {
        val expected = "test"
        val test = String.Empty

        assert(expected != test)
    }

    @Test
    fun `should be empty or blank string`() {
        val expected = ""
        val test = "".isEmptyOrBlank()

        assert(expected.isEmptyOrBlank() == test)
    }

    @Test
    fun `should be another empty or blank string`() {
        val expected = " "
        val test = " ".isEmptyOrBlank()

        assert(expected.isEmptyOrBlank() == test)
    }

    @Test
    fun `should be null string`() {
        val expected: String? = null
        val test = true

        assert(expected.isNull() == test)
    }

    @Test
    fun `should not be null string`() {
        val expected = "test"
        val test = false

        assert(expected.isNull() == test)
    }

    @Test
    fun `should check md5`() {
        val expected = "null"
        val test = "37a6259cc0c1dae299a7866489dff0bd"

        assert(expected.md5() == test)
    }
}
