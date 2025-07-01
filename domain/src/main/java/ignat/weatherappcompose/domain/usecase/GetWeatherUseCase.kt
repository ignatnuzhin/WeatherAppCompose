package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    fun execute(weatherId: Long): Flow<WeatherModel> {
        return weatherRepository.getWeather(weatherId)
    }
}