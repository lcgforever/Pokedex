package com.chenguang.pokedex.network

import com.chenguang.pokedex.network.model.PokemonListResponse
import com.chenguang.pokedex.network.model.PokemonResponse
import retrofit2.Response

/**
 * Client interface to fetch pokemon data from network
 */
interface PokemonNetworkClient {

    suspend fun fetchPokemonList(offset: Int = 0): Response<PokemonListResponse>

    suspend fun fetchPokemonDataById(id: String): Response<PokemonResponse>
}
