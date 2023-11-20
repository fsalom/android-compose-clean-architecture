package com.example.learnwithme.domain.entity

import org.junit.Test


class CharacterTest {
    @Test
    fun doAction_getModel(){
        val character = Character(
            id = 0,
            name = "",
            status = "",
            species = "",
            image = "",
            isFavorite = false,
            page = 0,
            creationDate = 0
        )

        assert(character.id == 0)
    }
}