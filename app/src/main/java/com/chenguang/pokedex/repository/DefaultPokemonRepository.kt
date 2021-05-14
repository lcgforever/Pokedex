package com.chenguang.pokedex.repository

import com.chenguang.pokedex.model.PokemonInfo
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.toPokemonInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Default implementation for [PokemonRepository] to fetch pokemon data
 */
class DefaultPokemonRepository @Inject constructor(
    private val pokemonNetworkClient: PokemonNetworkClient
) : PokemonRepository {

    override fun fetchPokemonList(pageNumber: Int): Flow<List<PokemonInfo>> {
        return flow {
            val networkResponse = pokemonNetworkClient.fetchPokemonList(pageNumber)
            if (networkResponse.isSuccessful) {
                val pokemonListResponse = networkResponse.body()!!
                emit(pokemonListResponse.results.map { it.toPokemonInfo() })
            } else {
                Timber.e("Fetch pokemon list failed: ${networkResponse.message()}")
                error("Failed to fetch pokemon list")
            }
        }.flowOn(Dispatchers.IO)
    }
}
