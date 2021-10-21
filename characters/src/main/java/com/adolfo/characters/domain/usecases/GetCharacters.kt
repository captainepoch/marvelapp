package com.adolfo.characters.domain.usecases

import com.adolfo.characters.data.models.entity.CharactersEntity
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.functional.State
import com.adolfo.core.interactor.UseCase

class GetCharacters(
    private val repository: CharactersRepository
) : UseCase<UseCase.None, State<CharactersEntity>>() {

    override fun execute(params: None?) = repository.getCharacters()
}
