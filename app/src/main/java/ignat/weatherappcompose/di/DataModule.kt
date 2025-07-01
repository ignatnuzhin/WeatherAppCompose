package ignat.weatherappcompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ignat.weatherappcompose.data.repository.WeatherRepositoryImpl
import ignat.weatherappcompose.data.storage.WeatherDao
import ignat.weatherappcompose.data.storage.WeatherDatabase
import ignat.weatherappcompose.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDao: WeatherDao) : WeatherRepository {
        return WeatherRepositoryImpl(dao = weatherDao)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(@ApplicationContext context: Context) : WeatherDao {
        return WeatherDatabase.getInstance(context).weatherDao
    }

}