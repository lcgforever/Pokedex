package com.chenguang.pokedex.network

import com.chenguang.pokedex.network.model.PokemonListResponse
import com.chenguang.pokedex.network.model.PokemonResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Default implementation for [PokemonNetworkClient] to fetch pokemon data from network
 */
class DefaultPokemonNetworkClient @Inject constructor(
    private val pokemonNetworkService: PokemonNetworkService
) : PokemonNetworkClient {

    override suspend fun fetchPokemonList(offset: Int): Response<PokemonListResponse> {
        return pokemonNetworkService.fetchPokemonList(offset = offset)
    }

    override suspend fun fetchPokemonDataByName(name: String): Response<PokemonResponse> {
        return pokemonNetworkService.fetchPokemonDataByName(name = name)
    }
}
