package com.chenguang.pokedex.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "typeId"], tableName = "pokemon_type_cross_ref_table")
data class PokemonTypeCrossRef(
    val pokemonId: String,
    val typeId: String
)

@Entity(primaryKeys = ["pokemonId", "abilityId"], tableName = "pokemon_ability_cross_ref_table")
data class PokemonAbilityCrossRef(
    val pokemonId: String,
    val abilityId: String
)
