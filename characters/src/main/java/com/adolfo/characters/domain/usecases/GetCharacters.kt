package com.adolfo.characters.domain.usecases

import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.extensions.orEmpty
import com.adolfo.core.functional.State
import com.adolfo.core.interactor.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCharacters(
    private val repository: CharactersRepository
) : UseCase<GetCharacters.Params, State<CharactersView>>() {

    override suspend fun execute(params: Params?) = withContext(Dispatchers.IO) {
        repository.getCharacters(
            params?.offset,
            params?.isPaginated.orEmpty(),
            params?.limit
        )
    }

    data class Params(
        val offset: Int,
        val isPaginated: Boolean?,
        val limit: Int? = CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT
    )
}
