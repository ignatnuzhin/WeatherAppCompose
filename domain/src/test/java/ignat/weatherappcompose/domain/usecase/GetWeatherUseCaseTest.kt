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

class GetWeatherUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @Test
    fun `should return weatherModel by id from repository`(){
        runBlocking {
            val getWeatherUseCase = GetWeatherUseCase(weatherRepository = weatherRepository)
            val testId = 1L
            val testWeather = WeatherModel(weatherId = testId, weatherCityName = "test")

            whenever(weatherRepository.getWeather(testId)).thenReturn(flowOf(testWeather))

            val actual = getWeatherUseCase.execute(testId).first()

            Assertions.assertEquals(testWeather, actual)
            verify(weatherRepository).getWeather(testId)
        }
    }

    @Test
    fun `should return new weatherModel if cant find by id`(){
        runBlocking {
            val getWeatherUseCase = GetWeatherUseCase(weatherRepository = weatherRepository)
            val testId = 1L
            val newWeather = WeatherModel()

            whenever(weatherRepository.getWeather(1L)).thenReturn(flowOf(newWeather))

            val actual = getWeatherUseCase.execute(testId).first()

            Assertions.assertEquals(newWeather, actual)
            verify(weatherRepository).getWeather(testId)
        }
    }

}