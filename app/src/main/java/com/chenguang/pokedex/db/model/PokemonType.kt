package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chenguang.pokedex.R

@Entity(tableName = "pokemon_type_table")
data class PokemonType(
    @PrimaryKey val typeId: String,
    @ColumnInfo(name = "slot") val slot: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
) {

    fun getColor(): Int {
        return when (typeId) {
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
