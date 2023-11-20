package com.example.learnwithme.presentation.navigation

sealed class Screen(val route: String) {
    object CharacterList : Screen("character/list")
    object CharacterDetail : Screen("character/detail/{id}") {
        fun getRouteFor(id: Int) =  CharacterDetail.route.replace(oldValue = "{id}", newValue = "$id")
    }
}