package ignat.weatherappcompose.data.repository

import ignat.weatherappcompose.data.storage.Weather

data class SearchResult(
    val main: Weather,
    val wind: WindInfo,
    val sys: SysInfo,
    val timezone: Int = 0
)

data class SysInfo(
    var sunset: Long = 0L,
    var sunrise: Long = 0L
)

data class WindInfo(
    var speed: Double = 0.0
)