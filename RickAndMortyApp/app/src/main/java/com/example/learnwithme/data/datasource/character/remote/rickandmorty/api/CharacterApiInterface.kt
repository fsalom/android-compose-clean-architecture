package com.example.learnwithme.data.datasource.character.remote.rickandmorty.api

import com.example.learnwithme.data.datasource.character.remote.rickandmorty.dto.CharacterDTO
import com.example.learnwithme.data.datasource.character.remote.rickandmorty.dto.CharactersInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiInterface {
    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharactersInfoDTO>

    @GET("api/character")
    suspend fun getCharactersFor(@Query("name") text: String, @Query("page") page: Int): Response<CharactersInfoDTO>

    @GET("api/character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<CharacterDTO>
}