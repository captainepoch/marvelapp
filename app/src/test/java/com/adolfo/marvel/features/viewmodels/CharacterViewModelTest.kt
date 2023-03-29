package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel

    private var repository = mockk<CharactersRepository>()
    private var getCharacterDetail = mockk<GetCharacterDetail>()

    private val characterObserver = mockk<Observer<CharacterView>>()
    private val errorObserver = mockk<Observer<Failure>>()

    @Before
    fun setup() {
        getCharacterDetail = GetCharacterDetail(repository)

        viewModel = CharacterViewModel(SavedStateHandle(), getCharacterDetail).apply {
            character.observeForever(characterObserver)
            failure.observeForever(errorObserver)
        }
    }

    @Test
    fun `should get character`() = runTest {
        val expectedResult = Success(CharacterEntity.empty().toCharacter().toCharacterView())

        val channel = Channel<State<CharacterView>>()
        val flow = channel.consumeAsFlow()

        coEvery { repository.getCharacter(0) } returns flow

        launch {
            channel.send(expectedResult)
        }

        viewModel.getCharacterDetail(0)

        coVerify {
            characterObserver.onChanged(expectedResult.data)
        }
    }
}
