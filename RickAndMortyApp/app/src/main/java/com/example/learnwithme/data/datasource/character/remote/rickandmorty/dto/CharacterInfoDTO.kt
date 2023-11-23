package com.example.learnwithme.data.datasource.character.remote.rickandmorty.dto

import com.example.learnwithme.domain.entity.Pagination
import com.google.gson.annotations.SerializedName

data class CharactersInfoDTO(
    @SerializedName("info") val info: PaginationDTO? = PaginationDTO(),
    @SerializedName("results") val results : List<CharacterDTO> = listOf()
)

data class PaginationDTO(
    @SerializedName("count") var count : Int? = 0,
    @SerializedName("pages") var pages : Int? = 0,
    @SerializedName("next") var next  : String? = "null",
    @SerializedName("prev") var prev  : String? = ""
)

fun CharactersInfoDTO.toDomain(): Pagination =
    Pagination(
        hasNextPage = info?.next != null,
        characters = results.map { it.toDomain() }
    )
