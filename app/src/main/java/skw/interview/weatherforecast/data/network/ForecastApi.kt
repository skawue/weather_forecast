package skw.interview.weatherforecast.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import skw.interview.weatherforecast.BuildConfig
import skw.interview.weatherforecast.domain.model.Forecast

interface ForecastApi {

    @GET("weather")
    suspend fun getForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appId") apiKey: String = BuildConfig.API_KEY
    ): Forecast
}