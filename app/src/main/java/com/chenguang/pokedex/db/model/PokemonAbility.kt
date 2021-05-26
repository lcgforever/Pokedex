package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_ability_table")
data class PokemonAbility(
    @PrimaryKey val abilityId: String,
    @ColumnInfo(name = "slot") val slot: Int,
    @ColumnInfo(name = "is_hidden") val isHidden: Boolean,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String
)
