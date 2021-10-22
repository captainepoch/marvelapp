package com.adolfo.characters.domain.usecases

import com.adolfo.characters.data.models.entity.CharacterEntity
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.functional.State
import com.adolfo.core.interactor.UseCase

class GetCharacterDetail(
    private val repository: CharactersRepository
) : UseCase<GetCharacterDetail.Params, State<CharacterEntity>>() {

    override fun execute(params: Params?) = repository.getCharacter(params?.id)

    data class Params(val id: Int?)
}
