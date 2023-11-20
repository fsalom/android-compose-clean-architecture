package com.example.learnwithme.data.datasource.character.remote.rickandmorty.dto

import com.example.learnwithme.domain.entity.Character
import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("status") val status: String? = null,
    @SerializedName("species") val species: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("origin") val origin: LocationDTO? = null,
    @SerializedName("location") val location: LocationDTO? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("episode") val episodes: List<String>? = null
)

fun CharacterDTO.toDomain(): Character =
    Character(
        id = id ?: 0,
        name = name ?: "",
        status = status ?: "",
        species = species ?: "",
        image = image ?: ""
    )

