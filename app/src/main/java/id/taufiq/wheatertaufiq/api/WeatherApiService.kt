package id.taufiq.wheatertaufiq.api

import id.taufiq.wheatertaufiq.model.WeatherApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created By Taufiq on 12/28/2020.
 *
 */
interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") query: String,
        @Query("appid") apiId: String
    ): Response<WeatherApi>
}