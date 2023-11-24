package com.example.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.converter.data.datasource.coin.remote.coincap.RemoteCoinDataSource
import com.example.converter.data.repositories.coin.CoinRepository
import com.example.converter.domain.usecases.coin.CoinUseCase
import com.example.converter.helper.interceptor.LoggingInterceptor
import com.example.converter.helper.logger.logcat.Logger
import com.example.converter.manager.network.NetworkRetrofitManager
import com.example.converter.presentation.converter.ConverterView
import com.example.converter.presentation.converter.ConverterViewModel
import com.example.converter.ui.theme.ConverterTheme
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val okHttpClient = OkHttpClient
                    .Builder()
                    .addInterceptor(LoggingInterceptor(
                        Logger("LOGGER_IDENTIFIER", Logger.Style.COMPLETE))
                    )
                    .build()

                    val remoterDataSource = RemoteCoinDataSource(
                        network = NetworkRetrofitManager(),
                        retrofit = Retrofit
                            .Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("https://api.coincap.io/v2/")
                            .client(okHttpClient)
                            .build())
                    val repository = CoinRepository(remoteDataSource = remoterDataSource)
                    val useCase = CoinUseCase(repository = repository)
                    val viewModel = ConverterViewModel(useCase = useCase)
                    ConverterView(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConverterTheme {
        Greeting("Android")
    }
}