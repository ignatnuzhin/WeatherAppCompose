package ignat.weatherappcompose.data.repository

import ignat.weatherappcompose.data.repository.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getSearchResult(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = "c9ed4bcbf22c12af9a7fef949eaf58d2"
    ): SearchResult
}