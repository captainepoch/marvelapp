package com.adolfo.marvel.features.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepositoryImp
import com.adolfo.characters.domain.usecases.GetCharacterDetail
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.State
import com.adolfo.core.functional.State.Success
import com.adolfo.core_testing.CoroutineTestRule
import com.adolfo.marvel.features.character.view.viewmodel.CharacterViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CharacterViewModel

    private var repository = mock<CharactersRepositoryImp>()
    private var getCharacterDetail = mock<GetCharacterDetail>()

    private val characterObserver = mock<Observer<CharacterView>>()
    private val errorObserver = mock<Observer<Failure>>()

    @Before
    fun setup() {
        getCharacterDetail = GetCharacterDetail(repository)

        viewModel = CharacterViewModel(SavedStateHandle(), getCharacterDetail).apply {
            character.observeForever(characterObserver)
            failure.observeForever(errorObserver)
        }
    }

    @Test
    fun `should get character`() = coroutinesRule.dispatcher.runBlockingTest {
        val expectedResult = Success(CharacterEntity.empty().toCharacter().toCharacterView())
        val expectedError = Failure.Throwable(Throwable())

        val channel = Channel<State<CharacterView>>()
        val flow = channel.consumeAsFlow()

        doReturn(flow)
            .whenever(repository)
            .getCharacter(0)

        launch {
            channel.send(expectedResult)
            channel.close(expectedError.throwable)
        }

        viewModel.getCharacterDetail(0)

        verify(characterObserver).onChanged(expectedResult.data)
        verify(errorObserver).onChanged(expectedError)
    }
}
