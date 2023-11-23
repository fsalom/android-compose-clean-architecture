package com.example.learnwithme.data.datasource.character.remote.disney.dto

import com.example.learnwithme.data.datasource.character.remote.rickandmorty.dto.LocationDTO
import com.example.learnwithme.domain.entity.Character
import com.google.gson.annotations.SerializedName

data class DisneyCharacterDTO(
    @SerializedName("_id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("imageUrl") val image: String? = null
)

fun DisneyCharacterDTO.toDomain(): Character =
    Character(
        id = id ?: 0,
        name = name ?: "",
        image = image ?: ""
    )

