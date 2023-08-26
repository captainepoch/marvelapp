package com.adolfo.characters.domain.usecases

import com.adolfo.characters.data.models.view.CharacterView
import com.adolfo.characters.domain.repository.CharactersRepository
import com.adolfo.core.exception.Failure
import com.adolfo.core.functional.Either
import com.adolfo.core.interactor.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterDetail(
    private val repository: CharactersRepository
) : UseCase<GetCharacterDetail.Params, Either<Failure, CharacterView>>() {

    override suspend fun execute(params: Params?): Flow<Either<Failure, CharacterView>> = flow {
        emit(repository.getCharacter(params?.id))
    }

    data class Params(val id: Int?)
}
