package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository

class SaveWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(weatherModel: WeatherModel): Boolean {
       return weatherRepository.saveWeather(weatherModel)
    }
}