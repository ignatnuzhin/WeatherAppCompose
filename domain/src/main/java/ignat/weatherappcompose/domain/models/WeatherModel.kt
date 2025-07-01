package ignat.weatherappcompose.domain.models

data class WeatherModel(
    var weatherId: Long = 0L,
    var weatherCityName: String = "",
    var feelsLike: String = "",
    var temperature: String = "",
    var windSpeed: String = "",
    var sunset: String = "",
    var sunrise: String = ""
)