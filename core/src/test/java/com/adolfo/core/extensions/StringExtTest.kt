package com.adolfo.core.extensions

import io.mockk.clearAllMocks
import java.util.Locale
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class StringExtTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun setUp() {
        val locale = Locale("en", "IE")
        Locale.setDefault(locale)
    }

    @After
    fun cleanUp() {
        clearAllMocks()
    }

    @Test
    fun `should be empty string`() {
        val expected = ""
        val test = String.empty()

        assert(expected == test)
    }

    @Test
    fun `should be not empty string`() {
        val expected = "test"
        val test = String.empty()

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
        val expected: String = "test"
        val test = false

        assert(expected.isNull() == test)
    }

    @Test
    fun `should check md5`() {
        val expected: String = "null"
        val test = "37a6259cc0c1dae299a7866489dff0bd"

        assert(expected.md5() == test)
    }
}
