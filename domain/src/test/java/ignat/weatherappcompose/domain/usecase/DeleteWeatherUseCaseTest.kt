package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeleteWeatherUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @Test
    fun `should delete weather and return true`(){

        val testWeather = WeatherModel(weatherCityName = "test city")
        val deleteWeatherUseCase = DeleteWeatherUseCase(weatherRepository = weatherRepository)

        runBlocking{
            whenever(weatherRepository.deleteWeather(testWeather)).thenReturn(true)
            val actual = deleteWeatherUseCase.execute(testWeather)
            verify(weatherRepository).deleteWeather(testWeather)
            assertTrue(actual)
        }
    }
}


