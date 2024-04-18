package com.example.pokequiz.model.service.module

import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.model.service.impl.PokemonServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: PokemonServiceImpl): PokemonService
}