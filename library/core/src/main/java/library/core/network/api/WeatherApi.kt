package library.core.network.api

import library.core.network.models.LocationData
import library.core.network.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String
    ): Response<WeatherData>

    @GET(("data/2.5/weather"))
    suspend fun getLocation(
        @Query("q") name: String,
        @Query("appid") appid: String
    ): Response<LocationData>
}
