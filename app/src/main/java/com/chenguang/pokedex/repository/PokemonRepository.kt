package com.chenguang.pokedex.repository

import com.chenguang.pokedex.model.PokemonDetails
import kotlinx.coroutines.flow.Flow

/**
 * Client repository to handle fetching of pokemon data, regardless of locally or remotely
 */
interface PokemonRepository {

    fun fetchPokemonData(id: String): Flow<PokemonDetails>
}
