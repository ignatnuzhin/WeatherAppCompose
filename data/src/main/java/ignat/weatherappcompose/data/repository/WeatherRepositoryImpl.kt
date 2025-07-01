package ignat.weatherappcompose.data.repository

import android.util.Log
import ignat.weatherappcompose.data.storage.Weather
import ignat.weatherappcompose.data.storage.WeatherDao
import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.SimpleTimeZone
import java.util.TimeZone
import kotlin.math.round
import kotlin.time.Duration.Companion.milliseconds


class WeatherRepositoryImpl(private val dao: WeatherDao) : WeatherRepository {

    override fun getAllWeathers(): Flow<List<WeatherModel>> {
        return dao.getAll().map { list ->
            list.map { mapToDomain(it ?: Weather()) }
        }
    }

    override fun getWeather(weatherId: Long): Flow<WeatherModel> {
        return dao.get(weatherId).map {
            mapToDomain(it ?: Weather())
        }
    }

    override suspend fun getWeatherFromAPI(cityName: String): WeatherModel {
        return mapToDomain(fetchWeather(cityName))
    }

    override suspend fun saveWeather(weatherModel: WeatherModel): Boolean {
        dao.insert(mapToStorage(weatherModel))
        return true
    }

    override suspend fun updateWeather(weatherModel: WeatherModel): Boolean {
        if (weatherModel.weatherId != 0L) {
            dao.update(fetchWeather(weatherModel.weatherCityName, weatherModel.weatherId))
            return true
        }
        return false
    }

    override suspend fun deleteWeather(weatherModel: WeatherModel): Boolean {
        val weather = mapToStorage(weatherModel)
        dao.delete(weather)
        return true
    }

    private suspend fun fetchWeather(cityName: String, weatherId: Long = 0L): Weather {
        val newWeather = Weather()
        try {
            val response = RetrofitClient.weatherApiService.getSearchResult(city = cityName)
            newWeather.weatherId = weatherId
            newWeather.temperature = round(response.main.temperature.toFloat()).toInt()
                .let { if (it > 0) "+$it" else it.toString() }
            newWeather.feelsLike = round(response.main.temperature.toFloat()).toInt()
                .let { if (it > 0) "+$it" else it.toString() }
            newWeather.weatherCityName =
                cityName.replaceFirstChar { if (it.isLowerCase()) it.titlecaseChar() else it }
            newWeather.windSpeed = response.wind.speed.toString()
            newWeather.sunset = formatUnixTime(response.sys.sunset, response.timezone)
            newWeather.sunrise = formatUnixTime(response.sys.sunrise, response.timezone)
        } catch (e: Exception) {
            newWeather.weatherId = -1L
            Log.e("AddWeatherViewModel", "Download error")
        }
        return newWeather
    }

    private fun formatUnixTime(unixTime: Long, timezone: Int): String {
        val sdf = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        sdf.timeZone = java.util.TimeZone.getTimeZone("GMT")
        val correctedTime = (unixTime + timezone).times(1000)
        return sdf.format(java.util.Date(correctedTime))
    }

    private fun mapToStorage(weatherModel: WeatherModel): Weather {
        return Weather(
            weatherId = weatherModel.weatherId,
            weatherCityName = weatherModel.weatherCityName,
            feelsLike = weatherModel.feelsLike,
            temperature = weatherModel.temperature,
            windSpeed = weatherModel.windSpeed,
            sunset = weatherModel.sunset,
            sunrise = weatherModel.sunrise
        )
    }

    private fun mapToDomain(weather: Weather): WeatherModel {
        return WeatherModel(
            weatherId = weather.weatherId,
            weatherCityName = weather.weatherCityName,
            feelsLike = weather.feelsLike,
            temperature = weather.temperature,
            windSpeed = weather.windSpeed,
            sunset = weather.sunset,
            sunrise = weather.sunrise
        )
    }

}