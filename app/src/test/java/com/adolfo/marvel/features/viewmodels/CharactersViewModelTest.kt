package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel
    private val getCharacters = mockk<GetCharacters>()
    private val charactersObserver = mockk<Observer<CharactersView>>()

    @Before
    fun setup() {
        viewModel = CharactersViewModel(SavedStateHandle(), getCharacters)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should emit get characters`() = runTest {
        viewModel.characters.observeForever(charactersObserver)
        val expectedResult = Success(CharactersView(listOf(), isFullEmpty = true))

        val flow = MutableStateFlow(expectedResult)
        every {
            getCharacters(GetCharacters.Params(0, false))
        } returns flow

        viewModel.getCharacters(false)

        verify {
            charactersObserver.onChanged(expectedResult.data)
        }
    }
}
