package com.chenguang.pokedex.model

import com.chenguang.pokedex.R

/**
 * Client domain model for pokemon detail information
 */
data class PokemonDetails(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Float,
    val weight: Float,
    val types: List<PokemonType>,
    val stats: List<PokemonStat>,
    val abilities: List<PokemonAbility>
)

data class PokemonType(
    val id: String,
    val slot: Int,
    val name: String,
    val url: String
) {

    fun getColor(): Int {
        return when (id) {
            "1" -> R.color.normal
            "2" -> R.color.fighting
            "3" -> R.color.flying
            "4" -> R.color.poison
            "5" -> R.color.ground
            "6" -> R.color.rock
            "7" -> R.color.bug
            "8" -> R.color.ghost
            "9" -> R.color.steel
            "10" -> R.color.fire
            "11" -> R.color.water
            "12" -> R.color.grass
            "13" -> R.color.electric
            "14" -> R.color.psychic
            "15" -> R.color.ice
            "16" -> R.color.dragon
            "17" -> R.color.dark
            "18" -> R.color.fairy
            else -> R.color.other
        }
    }
}

data class PokemonStat(
    val id: String,
    val value: Int,
    val name: String,
    val url: String
) {

    /**
     * We only want to display some stats not all of them
     */
    fun isVisible(): Boolean {
        return "1236".contains(id)
    }

    fun getColor(): Int {
        return when (id) {
            "1" -> R.color.hp
            "2" -> R.color.attack
            "3" -> R.color.defense
            "6" -> R.color.speed
            else -> R.color.other
        }
    }

    fun getProgress(): Int {
        return value * 100 / 250
    }
}

data class PokemonAbility(
    val id: String,
    val slot: Int,
    val isHidden: Boolean,
    val name: String,
    val url: String
)
