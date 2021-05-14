package com.chenguang.pokedex.network

import com.chenguang.pokedex.network.model.PokemonListResponse
import com.chenguang.pokedex.network.model.PokemonResponse
import com.chenguang.pokedex.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit implemented facade service to fetch pokemon data from PokeAPI
 */
interface PokemonNetworkService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = Constants.POKEMON_LIST_PAGE_SIZE,
        @Query("offset") offset: Int = 0
    ): Response<PokemonListResponse>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonDataByName(
        @Path("name") name: String
    ): Response<PokemonResponse>
}
