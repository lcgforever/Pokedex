package com.chenguang.pokedex.model

import com.chenguang.pokedex.db.model.PokemonAbility
import com.chenguang.pokedex.db.model.PokemonStat
import com.chenguang.pokedex.db.model.PokemonType

/**
 * Client domain model for pokemon detail information
 */
data class PokemonDetails(
    val id: String,
    val name: String,
    val baseExperience: Int,
    val height: Float,
    val weight: Float,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val abilities: List<PokemonAbility>
)
