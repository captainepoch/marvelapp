package com.adolfo.characters.domain.usecases

import com.adolfo.characters.core.utils.CharactersConstants
import com.adolfo.characters.data.models.view.CharactersView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.extensions.orEmpty
import com.adolfo.core.functional.State
import com.adolfo.core.interactor.UseCase

class GetCharacters(
    private val repository: CharactersRepository
) : UseCase<GetCharacters.Params, State<CharactersView>>() {

    override fun execute(params: Params?) =
        repository.getCharacters(
            params?.offset,
            params?.isPaginated.orEmpty(),
            params?.limit
        )

    data class Params(
        val offset: Int,
        val isPaginated: Boolean,
        val limit: Int? = CharactersConstants.Services.DEFAULT_CHARACTERS_LIMIT
    )
}
