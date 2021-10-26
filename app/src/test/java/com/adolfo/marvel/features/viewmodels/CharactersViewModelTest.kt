package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacters
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharactersViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharactersViewModel

    private var repository = mock<CharactersRepositoryImp>()
    private var getCharacters = mock<GetCharacters>()

    private val charactersObserver = mock<Observer<CharactersView>>()
    private val errorObserver = mock<Observer<Failure>>()

    @Before
    fun setup() {
        getCharacters = GetCharacters(repository)

        viewModel = CharactersViewModel(SavedStateHandle(), getCharacters).apply {
            characters.observeForever(charactersObserver)
            failure.observeForever(errorObserver)
        }
    }

    @Test
    fun `should emit get characters`() = coroutinesRule.dispatcher.runBlockingTest {
        val expectedResult = Success(
            CharactersView(listOf(), isFullEmpty = true)
        )
        val expectedError = Failure.Throwable(Throwable())

        val channel = Channel<State<CharactersView>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .whenever(repository)
            .getCharacters(0, false, 15)

        launch {
            channel.send(expectedResult)
            channel.close(expectedError.throwable)
        }

        viewModel.getCharacters(false)

        verify(charactersObserver).onChanged(expectedResult.data)
        verify(errorObserver).onChanged(expectedError)
    }
}
