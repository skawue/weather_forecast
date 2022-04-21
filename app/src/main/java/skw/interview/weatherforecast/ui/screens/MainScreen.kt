package skw.interview.weatherforecast.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import skw.interview.weatherforecast.R
import skw.interview.weatherforecast.ui.theme.WeatherForecastTheme

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState

    MainScreenContent(uiState)
}

@Composable
fun MainScreenContent(uiState: MainUiState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForecastContent(uiState)
        }
    }
}

@Composable
fun ForecastContent(uiState: MainUiState) {
    val uiData = uiState.data
    val textColor = if (uiState.isError || uiState.loading) Color.Gray else Color.Black

    Column(
        modifier = Modifier
            .width(200.dp)
    ) {
        Row {
            Box(modifier = Modifier.size(24.dp)) {
                if (uiState.loading) {
                    CircularProgressIndicator(strokeWidth = 2.dp)
                } else {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = if (uiState.isError) Color.Red else Color.Green
                    )
                }
            }
            Text(
                text = uiData.locationName,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Text(
            text = uiData.temperature,
            fontSize = 52.sp,
            color = textColor
        )
        AsyncImage(
            model = uiData.iconFilePath,
            contentDescription = "Weather icon",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp),
        )
        Text(
            text = uiData.description,
            fontSize = 18.sp,
            color = textColor,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherForecastTheme {
        MainScreenContent(
            MainUiState(
                loading = false,
                data = MainUiData(
                    "Warsaw, Warsaw, Warsaw, Warsaw",
                    "120 CC",
                    "10d",
                    "rain and something else"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultLoadingPreview() {
    WeatherForecastTheme {
        MainScreenContent(
            MainUiState(
                loading = true,
                data = MainUiData(
                    "Warsaw, Warsaw, Warsaw, Warsaw",
                    "120 CC",
                    "10d",
                    "rain and something else"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultErrorPreview() {
    WeatherForecastTheme {
        MainScreenContent(
            MainUiState(
                error = Throwable(),
                data = MainUiData(
                    "Warsaw, Warsaw, Warsaw, Warsaw",
                    "120 CC",
                    "10d",
                    "rain and something else"
                )
            )
        )
    }
}