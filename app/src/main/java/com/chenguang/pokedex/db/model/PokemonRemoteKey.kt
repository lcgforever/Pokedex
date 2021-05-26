package com.chenguang.pokedex.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_remote_key_table")
data class PokemonRemoteKey(
    @PrimaryKey val label: String,
    @ColumnInfo(name = "offset") val offset: Int?
)
