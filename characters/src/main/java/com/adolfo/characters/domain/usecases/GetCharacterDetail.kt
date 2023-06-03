package com.adolfo.characters.domain.usecases

import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.functional.State
import com.adolfo.core.interactor.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCharacterDetail(
    private val repository: CharactersRepository
) : UseCase<GetCharacterDetail.Params, State<CharacterView>>() {

    override suspend fun execute(params: Params?) = withContext(Dispatchers.IO) {
        repository.getCharacter(params?.id)
    }

    data class Params(val id: Int?)
}
