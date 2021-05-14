package com.chenguang.pokedex.repository

import com.chenguang.pokedex.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

/**
 * Client repository to handle fetching of pokemon data, regardless of locally or remotely
 */
interface PokemonRepository {

    fun fetchPokemonList(pageNumber: Int = 0): Flow<List<PokemonInfo>>
}
