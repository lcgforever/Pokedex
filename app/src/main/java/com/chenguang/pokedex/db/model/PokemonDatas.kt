package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "pokemon_data_table")
data class PokemonData(
    @PrimaryKey val pokemonId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "base_experience") val baseExperience: Int,
    @ColumnInfo(name = "height") val height: Float,
    @ColumnInfo(name = "weight") val weight: Float
)

/**
 * Define one to many relationship between pokemon data and pokemon stat
 */
data class PokemonDataWithStats(
    @Embedded val pokemonData: PokemonData,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "ownerId"
    )
    val stats: List<PokemonStat>
)

/**
 * Define many to many relationship between pokemon data and pokemon type
 */
data class PokemonDataWithTypes(
    @Embedded val pokemonData: PokemonData,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<PokemonType>
)

/**
 * Define many to many relationship between pokemon data and pokemon ability
 */
data class PokemonDataWithAbilities(
    @Embedded val pokemonData: PokemonData,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "abilityId",
        associateBy = Junction(PokemonAbilityCrossRef::class)
    )
    val abilities: List<PokemonAbility>
)
