package com.chenguang.pokedex.di

import com.chenguang.pokedex.network.DefaultPokemonNetworkClient
import com.chenguang.pokedex.network.NetworkRequestLoggingInterceptor
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.PokemonNetworkService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    abstract fun providePokemonNetworkClient(
        impl: DefaultPokemonNetworkClient
    ): PokemonNetworkClient

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(NetworkRequestLoggingInterceptor())
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun providePokemonNetworkService(retrofit: Retrofit): PokemonNetworkService {
            return retrofit.create(PokemonNetworkService::class.java)
        }
    }
}
