package skw.interview.weatherforecast.data.repository

import android.location.Location
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import skw.interview.weatherforecast.data.network.ForecastApi
import skw.interview.weatherforecast.data.provider.LocationProvider
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastAPI: ForecastApi,
    private val locationProvider: LocationProvider
) {
    suspend fun getWeatherForecast() = withContext(Dispatchers.IO) {
        val location = locationProvider.getLastLocation() ?: Location("")

        forecastAPI.getForecast(location.latitude, location.longitude)
    }
}