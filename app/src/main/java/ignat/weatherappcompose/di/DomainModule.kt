package ignat.weatherappcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ignat.weatherappcompose.domain.repository.WeatherRepository
import ignat.weatherappcompose.domain.usecase.DeleteWeatherUseCase
import ignat.weatherappcompose.domain.usecase.GetAllWeathersUseCase
import ignat.weatherappcompose.domain.usecase.GetWeatherFromAPIUseCase
import ignat.weatherappcompose.domain.usecase.GetWeatherUseCase
import ignat.weatherappcompose.domain.usecase.SaveWeatherUseCase
import ignat.weatherappcompose.domain.usecase.UpdateWeatherUseCase

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideDeleteWeatherUseCase(weatherRepository: WeatherRepository) : DeleteWeatherUseCase {
        return DeleteWeatherUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideGetAllWeathersUseCase(weatherRepository: WeatherRepository) : GetAllWeathersUseCase {
        return GetAllWeathersUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideGetWeatherFromAPIUseCase(weatherRepository: WeatherRepository) : GetWeatherFromAPIUseCase {
        return GetWeatherFromAPIUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository) : GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideSaveWeatherUseCase(weatherRepository: WeatherRepository) : SaveWeatherUseCase {
        return SaveWeatherUseCase(weatherRepository = weatherRepository)
    }

    @Provides
    fun provideUpdateWeatherUseCase(weatherRepository: WeatherRepository) : UpdateWeatherUseCase {
        return UpdateWeatherUseCase(weatherRepository = weatherRepository)
    }
}