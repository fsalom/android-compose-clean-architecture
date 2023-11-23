package com.example.learnwithme.data.datasource.character.remote.disney.api
import com.example.learnwithme.data.datasource.character.remote.disney.dto.DisneyInfoDTO
import com.example.learnwithme.data.datasource.character.remote.disney.dto.DisneySingleInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyApiInterFace {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<DisneyInfoDTO>
    @GET("character")
    suspend fun getCharactersFor(@Query("test") text: String, @Query("page") page: Int): Response<DisneyInfoDTO>
    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<DisneySingleInfoDTO>
}