package id.taufiq.wheatertaufiq.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Taufiq on 12/28/2020.
 *
 */

private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"


val logging = HttpLoggingInterceptor().apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

val client = OkHttpClient.Builder().run {
    addInterceptor(logging).build()
}


private val retrofitInstance = Retrofit.Builder().run {
    addConverterFactory(GsonConverterFactory.create())
    baseUrl(BASE_URL)
    client(client)
    build()
}


object myWeather {
    val weatherService: WeatherApiService by lazy {
        retrofitInstance.create(WeatherApiService::class.java)
    }

}


