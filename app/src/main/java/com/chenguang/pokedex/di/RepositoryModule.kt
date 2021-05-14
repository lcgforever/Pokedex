package com.chenguang.pokedex.di

import com.chenguang.pokedex.repository.DefaultPokemonRepository
import com.chenguang.pokedex.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun providePokemonRepository(impl: DefaultPokemonRepository): PokemonRepository
}
