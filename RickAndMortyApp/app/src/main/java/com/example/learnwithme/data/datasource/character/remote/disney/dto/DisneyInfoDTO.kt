package com.example.learnwithme.data.datasource.character.remote.disney.dto

import com.example.learnwithme.domain.entity.Pagination
import com.google.gson.annotations.SerializedName

data class DisneyInfoDTO(
    @SerializedName("info") val info: DisneyPaginationDTO? = DisneyPaginationDTO(),
    @SerializedName("data") val data : List<DisneyCharacterDTO> = listOf()
)

data class DisneySingleInfoDTO(
    @SerializedName("info") val info: DisneyPaginationDTO? = DisneyPaginationDTO(),
    @SerializedName("data") val data : DisneyCharacterDTO?
)

data class DisneyPaginationDTO(
    @SerializedName("nextPage") var next : String? = null
)

fun DisneyInfoDTO.toDomain(): Pagination =
    Pagination(
        hasNextPage = info?.next != null,
        characters = data.map { it.toDomain() }
    )
