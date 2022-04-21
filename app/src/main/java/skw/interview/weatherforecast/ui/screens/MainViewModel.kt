package skw.interview.weatherforecast.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skw.interview.weatherforecast.data.repository.ForecastRepository
import skw.interview.weatherforecast.domain.model.Forecast
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MainViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {

    var uiState by mutableStateOf(MainUiState())

    init {
        viewModelScope.launch {
            uiState = uiState.copy(
                error = null,
                loading = true,
            )
            uiState = try {
                val forecast = forecastRepository.getWeatherForecast()

                uiState.copy(
                    loading = false,
                    data = MainUiData(forecast)
                )
            } catch (ex: Exception) {
                uiState.copy(
                    error = ex,
                    loading = false,
                )
            }
        }
    }
}

data class MainUiState(
    val error: Throwable? = null,
    val loading: Boolean = false,
    val data: MainUiData = MainUiData()
) {
    val isError = error != null
}

data class MainUiData(
    val locationName: String = "-",
    val temperature: String = "- °C",
    val iconFilePath: String? = null,
    val description: String = "-"
) {
    constructor(model: Forecast) : this(
        locationName = model.name,
        temperature = "${model.main.temp.roundToInt()} °C",
        iconFilePath = "https://openweathermap.org/img/wn/${model.weather.firstOrNull()?.icon ?: ""}@2x.png",
        description = model.weather.firstOrNull()?.description?.capitalize() ?: "-"
    )
}
