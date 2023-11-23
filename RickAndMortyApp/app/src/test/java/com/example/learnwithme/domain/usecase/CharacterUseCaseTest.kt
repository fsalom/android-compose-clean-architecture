package com.example.learnwithme.domain.usecase

import com.example.learnwithme.data.repository.character.CharacterRepository
import com.example.learnwithme.domain.usecase.character.CharacterUseCase
import com.example.learnwithme.domain.usecase.character.CharacterUseCaseInterface
import com.example.learnwithme.domain.usecase.mocks.LocalCharacterDataSourceMock
import com.example.learnwithme.domain.usecase.mocks.RemoteCharacterDataSourceMock
import org.junit.Test

class CharacterUseCaseTest {

    val useCase = setup()
    private fun setup(): CharacterUseCaseInterface {
        var repository = CharacterRepository(
            remoteDataSource = RemoteCharacterDataSourceMock(),
            databaseDatasource = LocalCharacterDataSourceMock()
        )
        return CharacterUseCase(repository = repository)
    }

    @Test
    suspend fun getCharactersTest() {
        var results = useCase.getNextPageAndCharacters(page = 1)
    }
}