package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.chenguang.pokedex.R

@Entity(tableName = "pokemon_stat_table", primaryKeys = ["statId", "ownerId"])
data class PokemonStat(
    val statId: String,
    val ownerId: String,
    @ColumnInfo(name = "value") val value: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
) {

    /**
     * We only want to display some stats not all of them
     */
    fun isVisible(): Boolean {
        return "1236".contains(statId)
    }

    fun getColor(): Int {
        return when (statId) {
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
