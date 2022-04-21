package skw.interview.weatherforecast.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(val coord: Coord, val weather: List<Weather>, val main: Temp, val name: String) {

    @Serializable
    data class Coord(val lon: Double, val lat: Double)

    @Serializable
    data class Weather(val main: String, val description: String, val icon: String)

    @Serializable
    data class Temp(val temp: Double, val temp_min: Double, val temp_max: Double)
}