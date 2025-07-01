package ignat.weatherappcompose.data.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ignat.weatherappcompose.data.storage.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert
    suspend fun insert(weather: Weather)

    @Update
    suspend fun update(weather: Weather)

    @Delete
    suspend fun delete(weather: Weather)

    @Query("SELECT * FROM weather_table WHERE weatherId = :weatherId")
    fun get(weatherId: Long): Flow<Weather>

    @Query("SELECT * FROM weather_table ORDER BY weatherId DESC")
    fun getAll(): Flow<List<Weather>>

}