package ignat.weatherappcompose.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.presentation.EditWeatherViewModel
import ignat.weatherappcompose.ui.theme.MyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWeather(navController: NavController,
                weatherId: Long,
                onReturnToWeatherList: () -> Unit,
                viewModel: EditWeatherViewModel = hiltViewModel()) {

    var cityName by remember { mutableStateOf("") }
    val weatherModel by viewModel.weatherModel.observeAsState(WeatherModel())
    val weatherResultText by viewModel.weatherResultText.observeAsState("")
    val saveButtonEnabled by viewModel.saveButtonEnabled.observeAsState(false)
    val shouldReturnToWeatherList by viewModel.shouldReturnToWeatherList.observeAsState(false)

    val weatherBackgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xffd8c1d4), Color(0xfffb96a5),Color(0xfff3803c)),
    )

    LaunchedEffect(shouldReturnToWeatherList) {
        if (shouldReturnToWeatherList) {
            viewModel.onReturnedToWeatherList()
            onReturnToWeatherList()
        }
    }

    LaunchedEffect(weatherModel.weatherId) {
        if (weatherModel.weatherId != 0L) {
            viewModel.getResultText(weatherModel)
            cityName = weatherModel.weatherCityName
        }
    }

    LaunchedEffect(weatherId) {
        viewModel.setWeatherId(weatherId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.returnToWeatherList() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Return to weather list",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if (weatherId != 0L) {
                        IconButton(
                            onClick = {
                                viewModel.deleteWeather()
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete weather",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MyBlue)
            )
        }
    ) { Column(
        modifier = Modifier
            .padding(it)
            .background(weatherBackgroundGradient)
            .fillMaxSize()
    ) {

            TextField(
                value = cityName,
                onValueChange = { newCityName -> cityName = newCityName },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Red
                )
            )

            Text(text = weatherResultText, modifier = Modifier.padding(5.dp).weight(1f).fillMaxSize(), fontSize = 16.sp)

            EditWeatherButtons(weatherId = weatherId,
                viewModel = viewModel,
                saveButtonEnabled = saveButtonEnabled,
                cityName = cityName)

        }
    }
}

@Composable
fun EditWeatherButtons(weatherId: Long,
                       viewModel: EditWeatherViewModel,
                       saveButtonEnabled: Boolean,
                       cityName: String){

    Button(onClick = { viewModel.fetchWeather(cityName) },
        modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Text("Выполнить поиск")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = {
            if (weatherId == 0L) {
                viewModel.saveWeather()
            } else {
                viewModel.updateWeather(weatherId = weatherId)
            }
        },
            enabled = saveButtonEnabled,
            modifier = Modifier.weight(1f).padding(5.dp)) {
            Text("Сохранить")
        }
        Button(onClick = { viewModel.returnToWeatherList() },
            modifier = Modifier.weight(1f).padding(5.dp)) {
            Text("Отмена")
        }
    }

}