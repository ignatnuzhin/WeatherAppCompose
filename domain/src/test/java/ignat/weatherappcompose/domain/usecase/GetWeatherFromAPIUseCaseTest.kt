package ignat.weatherappcompose.domain.usecase

import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.repository.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetWeatherFromAPIUseCaseTest {

    private val weatherRepository = mock<WeatherRepository>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(weatherRepository)
    }

    @Test
    fun `should return weather from api by city name`(){

        val testCityName = "test city"
        val testWeather = WeatherModel(weatherCityName = testCityName)
        val getWeatherFromAPIUseCase = GetWeatherFromAPIUseCase(weatherRepository = weatherRepository)

        runBlocking {
            whenever(weatherRepository.getWeatherFromAPI(cityName = testCityName)).thenReturn(testWeather)
            val actual = getWeatherFromAPIUseCase.execute(cityName = testCityName).weatherCityName
            val expected = testCityName
            verify(weatherRepository).getWeatherFromAPI(testCityName)
            Assertions.assertEquals(expected, actual)
        }
    }

    @Test
    fun `should return weather with -1 id if cant fetch weather`(){

        val testCityName = "test city"
        val testWeather = WeatherModel(weatherId = -1L)
        val getWeatherFromAPIUseCase = GetWeatherFromAPIUseCase(weatherRepository = weatherRepository)

        runBlocking {
            whenever(weatherRepository.getWeatherFromAPI(cityName = testCityName)).thenReturn(testWeather)
            val actual = getWeatherFromAPIUseCase.execute(cityName = testCityName).weatherId
            val expected = testWeather.weatherId
            verify(weatherRepository).getWeatherFromAPI(testCityName)
            Assertions.assertEquals(expected, actual)
        }
    }

}