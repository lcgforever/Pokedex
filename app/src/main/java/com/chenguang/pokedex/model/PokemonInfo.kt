package com.chenguang.pokedex.model

/**
 * Client domain model for pokemon basic information
 */
data class PokemonInfo(
    val id: String,
    val name: String,
    val url: String
) {

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites" +
                "/pokemon/other/official-artwork/$id.png"
    }
}
