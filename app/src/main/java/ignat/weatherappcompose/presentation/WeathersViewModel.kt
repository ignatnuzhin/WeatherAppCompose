package ignat.weatherappcompose.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ignat.weatherappcompose.domain.models.WeatherModel
import ignat.weatherappcompose.domain.usecase.GetAllWeathersUseCase
import ignat.weatherappcompose.domain.usecase.UpdateWeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeathersViewModel @Inject constructor(
    private val updateWeatherUseCase: UpdateWeatherUseCase,
    private val getAllWeathersUseCase: GetAllWeathersUseCase
) : ViewModel() {

    val weathers: LiveData<List<WeatherModel>> = getAllWeathersUseCase.execute().asLiveData()

    fun updateAllWeathers() {
        viewModelScope.launch {
            weathers.value?.forEach { weatherModel ->
                updateWeatherUseCase.execute(weatherModel = weatherModel)
            }
        }
    }
}