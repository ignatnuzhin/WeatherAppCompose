package ignat.weatherappcompose.domain.repository

import ignat.weatherappcompose.domain.models.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getAllWeathers(): Flow<List<WeatherModel>>

    fun getWeather(weatherId: Long): Flow<WeatherModel>

    suspend fun getWeatherFromAPI(cityName: String): WeatherModel

    suspend fun saveWeather(weatherModel: WeatherModel): Boolean

    suspend fun updateWeather(weatherModel: WeatherModel): Boolean

    suspend fun deleteWeather(weatherModel: WeatherModel): Boolean

}