package com.chenguang.pokedex.repository

import com.chenguang.pokedex.model.PokemonDetails
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.toPokemonDetails
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

    override fun fetchPokemonData(id: String): Flow<PokemonDetails> {
        return flow {
            val networkResponse = pokemonNetworkClient.fetchPokemonDataById(id)
            if (networkResponse.isSuccessful) {
                val pokemonResponse = networkResponse.body()!!
                emit(pokemonResponse.toPokemonDetails())
            } else {
                Timber.e("Fetch pokemon data failed: ${networkResponse.message()}")
                error("Failed to fetch pokemon data by id")
            }
        }.flowOn(Dispatchers.IO)
    }
}
