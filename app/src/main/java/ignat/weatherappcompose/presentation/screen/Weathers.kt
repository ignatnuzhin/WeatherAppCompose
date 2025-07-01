package ignat.weatherappcompose.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ignat.weatherappcompose.presentation.WeathersViewModel
import ignat.weatherappcompose.presentation.navigation.WeatherRoutes
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ignat.weatherappcompose.ui.theme.MyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Weathers(navController: NavController,
             viewModel: WeathersViewModel = hiltViewModel()
){

    val weathers by viewModel.weathers.observeAsState(emptyList())
    val weatherItemGradient = Brush.verticalGradient(
        colors = listOf(Color(0xfff4f4f4), Color(0xffeae6ff)),
    )
    val weatherBackgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xffd8c1d4), Color(0xfffb96a5),Color(0xfff3803c)),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "WeatherApp", color = Color.White)
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.updateAllWeathers() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Update all weathers",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MyBlue)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                content = {
                    Icon(imageVector = Icons.Filled.Add,
                        contentDescription = "Add weather")
                },
                onClick = {
                    navController
                        .navigate(WeatherRoutes.EditWeather.createRoute(0L)) }
            )
        }
    )
    {
        Column(
            modifier = Modifier.background(weatherBackgroundGradient)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it)
            ) {
                items(weathers) {
                    weather ->
                    Row(
                        modifier = Modifier.clickable {
                            navController
                                .navigate(
                                    WeatherRoutes
                                        .EditWeather
                                        .createRoute(weatherId = weather.weatherId)
                                )
                        }
                            .padding(10.dp)
                            .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
                            .background(brush = weatherItemGradient, shape = RoundedCornerShape(5.dp))
                            .border(border = BorderStroke(0.dp , Color.Transparent), shape = RoundedCornerShape(5.dp))
                            .padding(10.dp)

                    ) {
                        Text(text = weather.weatherCityName, modifier = Modifier.weight(1f), fontSize = 20.sp)
                        Text(text = weather.temperature, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}