package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetAllWeathersUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @Test
    fun `should return all saved weathers`(){
        runBlocking {
            val getWeatherUseCase = GetAllWeathersUseCase(weatherRepository = weatherRepository)
            val testWeather = listOf(WeatherModel(weatherCityName = "first"), WeatherModel(weatherCityName = "second"))

            whenever(weatherRepository.getAllWeathers()).thenReturn(flowOf(testWeather))

            val actual = getWeatherUseCase.execute().first()

            Assertions.assertEquals(testWeather, actual)
            verify(weatherRepository).getAllWeathers()
        }
    }
}