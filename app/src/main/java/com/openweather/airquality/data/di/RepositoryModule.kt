package com.openweather.airquality.data.di

import com.openweather.airquality.data.repository.OpenWeatherRepositoryImpl
import com.openweather.airquality.domain.repository.OpenWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindsOpenWeatherRepository(repository: OpenWeatherRepositoryImpl): OpenWeatherRepository
}

