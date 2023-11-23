package com.example.learnwithme.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.learnwithme.configuration.LOGGER_IDENTIFIER
import com.example.learnwithme.data.datasource.character.database.room.RoomCharacterDataSource
import com.example.learnwithme.data.datasource.character.remote.disney.RemoteDisneyCharactersDataSource
import com.example.learnwithme.data.datasource.character.remote.disney.api.DisneyApiInterFace
import com.example.learnwithme.data.datasource.character.remote.mock.MockCharacterDataSource
import com.example.learnwithme.data.datasource.character.remote.rickandmorty.RemoteCharactersDataSource
import com.example.learnwithme.data.manager.network.NetworkManager
import com.example.learnwithme.data.repository.character.CharacterRepository
import com.example.learnwithme.di.AppDatabase
import com.example.learnwithme.domain.usecase.character.CharacterUseCase
import com.example.learnwithme.helper.logger.logcat.Logger
import com.example.learnwithme.helper.interceptor.LoggingInterceptor
import com.example.learnwithme.presentation.detail.DetailCharactersView
import com.example.learnwithme.presentation.detail.DetailCharactersViewModel
import com.example.learnwithme.presentation.list.ListCharactersView
import com.example.learnwithme.presentation.list.ListCharactersViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    context: Context
) {
    val mockDatasource = MockCharacterDataSource()

    var okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(LoggingInterceptor(
            Logger(LOGGER_IDENTIFIER, Logger.Style.COMPLETE)
        ))
        .build()

    val rickAndMortyDatasource = RemoteCharactersDataSource(
        network = NetworkManager(),
        retrofit = Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/")
            .client(okHttpClient)
            .build()
    )

    val disneyDatasource = RemoteDisneyCharactersDataSource(
        disneyApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.disneyapi.dev/")
            .client(OkHttpClient())
            .build().create(DisneyApiInterFace::class.java),
        network = NetworkManager()
    )

    val roomCharacterDataSource = RoomCharacterDataSource(dao = AppDatabase(context).characterDao())

    val repository = CharacterRepository(
        remoteDataSource = rickAndMortyDatasource,
        databaseDatasource = roomCharacterDataSource
    )
    val useCase = CharacterUseCase(repository = repository)
    val vm = ListCharactersViewModel(useCase =  useCase)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.CharacterList.route) {
            ListCharactersView(viewModel = vm, navController = navController)
        }
        composable(
            route=Screen.CharacterDetail.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry->
            val character = navBackStackEntry.arguments?.getInt("id")?.let { it }
            if (character != null) {
                val vm = DetailCharactersViewModel(
                    id = character,
                    useCase =  useCase
                )
                DetailCharactersView(viewModel = vm)
            }
        }
    }
}