package com.example.pokequiz.model.service.module

import com.example.pokequiz.model.service.AccountService
import com.example.pokequiz.model.service.PokemonService
import com.example.pokequiz.model.service.ProfileService
import com.example.pokequiz.model.service.implementation.AccountServiceImpl
import com.example.pokequiz.model.service.implementation.PokemonServiceImpl
import com.example.pokequiz.model.service.implementation.ProfileServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun providePokemonService(impl: PokemonServiceImpl): PokemonService
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
    @Binds
    abstract fun provideProfileService(impl: ProfileServiceImpl): ProfileService
}