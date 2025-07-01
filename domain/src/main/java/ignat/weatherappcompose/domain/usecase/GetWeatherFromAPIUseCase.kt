package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository

class GetWeatherFromAPIUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(cityName: String): WeatherModel {
        return weatherRepository.getWeatherFromAPI(cityName)
    }
}