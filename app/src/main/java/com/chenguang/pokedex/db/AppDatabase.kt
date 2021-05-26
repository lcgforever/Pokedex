package com.chenguang.pokedex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chenguang.pokedex.db.dao.PokemonDataDao
import com.chenguang.pokedex.db.dao.PokemonInfoDao
import com.chenguang.pokedex.db.dao.PokemonRemoteKeyDao
import com.chenguang.pokedex.db.model.PokemonAbility
import com.chenguang.pokedex.db.model.PokemonAbilityCrossRef
import com.chenguang.pokedex.db.model.PokemonData
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.db.model.PokemonRemoteKey
import com.chenguang.pokedex.db.model.PokemonStat
import com.chenguang.pokedex.db.model.PokemonType
import com.chenguang.pokedex.db.model.PokemonTypeCrossRef

@Database(
    entities = [
        PokemonRemoteKey::class,
        PokemonInfo::class,
        PokemonData::class,
        PokemonType::class,
        PokemonStat::class,
        PokemonAbility::class,
        PokemonTypeCrossRef::class,
        PokemonAbilityCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonRemoteKeyDao(): PokemonRemoteKeyDao

    abstract fun pokemonInfoDao(): PokemonInfoDao

    abstract fun pokemonDataDao(): PokemonDataDao
}
