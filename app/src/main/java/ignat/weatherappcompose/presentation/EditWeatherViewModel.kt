package ignat.weatherappcompose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.usecase.DeleteWeatherUseCase
import ignat.weatherappcompose.domain.usecase.GetWeatherFromAPIUseCase
import ignat.weatherappcompose.domain.usecase.GetWeatherUseCase
import ignat.weatherappcompose.domain.usecase.SaveWeatherUseCase
import ignat.weatherappcompose.domain.usecase.UpdateWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditWeatherViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val deleteWeatherUseCase: DeleteWeatherUseCase,
    private val getWeatherFromAPIUseCase: GetWeatherFromAPIUseCase,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase,
    private val updateWeatherUseCase: UpdateWeatherUseCase
) : ViewModel() {

    val weatherId: Long = savedStateHandle["weatherId"] ?: 0L

    private lateinit var newWeather: WeatherModel
    var weatherModel = getWeatherUseCase.execute(weatherId).asLiveData()

    private val _shouldReturnToWeatherList = MutableLiveData<Boolean>(false)
    val shouldReturnToWeatherList: LiveData<Boolean>
        get() = _shouldReturnToWeatherList

    private val _weatherResultText = MutableLiveData<String>("")
    val weatherResultText: LiveData<String>
        get() = _weatherResultText

    private val _saveButtonEnabled = MutableLiveData<Boolean>(false)
    val saveButtonEnabled: LiveData<Boolean>
        get() = _saveButtonEnabled

    fun returnToWeatherList() {
        _shouldReturnToWeatherList.value = true
    }

    fun onReturnedToWeatherList() {
        _shouldReturnToWeatherList.value = false
    }

    fun deleteWeather() {
        viewModelScope.launch {
            deleteWeatherUseCase.execute(weatherModel = weatherModel.value!!)
        }
        returnToWeatherList()
    }

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            newWeather = getWeatherFromAPIUseCase.execute(cityName = cityName)
            if (newWeather.weatherId != -1L) {
                getResultText(weatherModel = newWeather)
                _saveButtonEnabled.value = true
            } else {
                _saveButtonEnabled.value = false
            }
        }
    }

    fun getResultText(weatherModel: WeatherModel) {
        _weatherResultText.value = 
            "Температура: ${weatherModel.temperature}" +
                "\nОщущается как: ${weatherModel.feelsLike}" +
                "\nСкорость ветра: ${weatherModel.windSpeed}" +
                "\nВосход: ${weatherModel.sunrise}" +
                "\nЗакат: ${weatherModel.sunset}"
    }

    fun saveWeather() {
        viewModelScope.launch {
            saveWeatherUseCase.execute(weatherModel = newWeather)
            returnToWeatherList()
        }
    }

    fun setWeatherId(Id: Long){
        savedStateHandle["weatherId"] = Id
    }

    fun updateWeather(weatherId: Long) {
        newWeather.weatherId = weatherId
        viewModelScope.launch {
            updateWeatherUseCase.execute(weatherModel = newWeather)
            returnToWeatherList()
        }
    }
}