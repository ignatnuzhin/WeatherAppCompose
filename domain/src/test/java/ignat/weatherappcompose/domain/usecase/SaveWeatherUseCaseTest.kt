package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SaveWeatherUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @Test
    fun `should save weather and return true`(){

        val testWeather = WeatherModel(weatherCityName = "test city")
        val saveWeatherUseCase = SaveWeatherUseCase(weatherRepository = weatherRepository)

        runBlocking{
            whenever(weatherRepository.saveWeather(weatherModel = testWeather)).thenReturn(true)
            val actual = saveWeatherUseCase.execute(weatherModel = testWeather)
            verify(weatherRepository).saveWeather(testWeather)
            assertTrue(actual)
        }
    }
}