package com.example.learnwithme.domain.entity

import org.junit.Test

class PaginationTest {
    @Test
    fun doAction_getModel(){
        val pagination = Pagination(
            hasNextPage = true,
            characters = emptyList()
        )

        assert(pagination.hasNextPage == true)
    }
}