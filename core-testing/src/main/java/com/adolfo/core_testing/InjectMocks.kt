package com.adolfo.core_testing

import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

class InjectMocks {

    companion object {
        fun create(testClass: Any) = TestRule { statement, _ ->
            MockitoAnnotations.initMocks(testClass)

            statement
        }
    }
}
