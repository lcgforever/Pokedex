package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_info_table")
data class PokemonInfo(
    @PrimaryKey val pokemonId: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
) {

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites" +
                "/pokemon/other/official-artwork/$pokemonId.png"
    }
}
