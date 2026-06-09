package com.apolo.tracking.di

import com.apolo.tracking.data.repository.AuthRepositoryImpl
import com.apolo.tracking.data.repository.VehicleRepositoryImpl
import com.apolo.tracking.domain.repository.AuthRepository
import com.apolo.tracking.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindVehicleRepository(impl: VehicleRepositoryImpl): VehicleRepository
}
