package ignat.weatherappcompose.data.storage

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather_table")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    var weatherId: Long = 0L,
    @ColumnInfo(name = "weather_city_name")
    var weatherCityName: String = "",
    @ColumnInfo(name = "feels_like")
    @SerializedName("feels_like")
    var feelsLike: String = "",
    @SerializedName("temp")
    var temperature: String = "",
    var windSpeed: String = "",
    var sunset: String = "",
    var sunrise: String = ""
)