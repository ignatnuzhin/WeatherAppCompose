package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UpdateWeatherUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(weatherRepository)
    }

    @Test
    fun `should update weather and return true`(){

        val testWeather = WeatherModel(weatherId = 2L)
        val updateWeatherUseCase = UpdateWeatherUseCase(weatherRepository = weatherRepository)

        runBlocking{
            whenever(weatherRepository.updateWeather(weatherModel = testWeather)).thenReturn(true)
            val actual = updateWeatherUseCase.execute(weatherModel = testWeather)
            verify(weatherRepository).updateWeather(weatherModel = testWeather)
            assertTrue(actual)
        }
    }

    @Test
    fun `should not update weather and return false`(){

        val testWeather = WeatherModel(weatherId = 0L)
        val updateWeatherUseCase = UpdateWeatherUseCase(weatherRepository = weatherRepository)

        runBlocking{
            whenever(weatherRepository.updateWeather(weatherModel = testWeather)).thenReturn(false)
            val actual = updateWeatherUseCase.execute(weatherModel = testWeather)
            verify(weatherRepository).updateWeather(weatherModel = testWeather)
            assertFalse(actual)
        }
    }
}