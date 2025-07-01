package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

class GetAllWeathersUseCase(private val weatherRepository: WeatherRepository) {
    fun execute(): Flow<List<WeatherModel>> {
        return weatherRepository.getAllWeathers()
    }
}