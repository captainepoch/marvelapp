package com.adolfo.characters.domain.usecases

import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.data.CharactersData
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.exception.Failure
import com.adolfo.core.extensions.orFalse
import com.adolfo.core.functional.Either
import com.adolfo.core.interactor.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCharacters(
    private val repository: CharactersRepository
) : UseCase<GetCharacters.Params, Either<Failure, CharactersView>>() {

    override suspend fun execute(params: Params?) = withContext(Dispatchers.IO) {
        repository.getCharacters(
            params?.offset,
            params?.isPaginated.orFalse(),
            params?.limit
        )
    }

    data class Params(
        val offset: Int,
        val isPaginated: Boolean?,
        val limit: Int? = CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT
    )
}
