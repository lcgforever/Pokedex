package com.chenguang.pokedex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.chenguang.pokedex.db.model.PokemonAbility
import com.chenguang.pokedex.db.model.PokemonAbilityCrossRef
import com.chenguang.pokedex.db.model.PokemonData
import com.chenguang.pokedex.db.model.PokemonDataWithAbilities
import com.chenguang.pokedex.db.model.PokemonDataWithStats
import com.chenguang.pokedex.db.model.PokemonDataWithTypes
import com.chenguang.pokedex.db.model.PokemonStat
import com.chenguang.pokedex.db.model.PokemonType
import com.chenguang.pokedex.db.model.PokemonTypeCrossRef

@Dao
interface PokemonDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonData(pokemonData: PokemonData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonTypeList(pokemonTypeList: List<PokemonType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonStatList(pokemonStatList: List<PokemonStat>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonAbilityList(pokemonAbilityList: List<PokemonAbility>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonTypeCrossRefList(crossRefList: List<PokemonTypeCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonAbilityCrossRefList(crossRefList: List<PokemonAbilityCrossRef>)

    @Query("SELECT * FROM pokemon_data_table WHERE pokemonId = :pokemonId")
    fun getPokemonDataById(pokemonId: String): PokemonData?

    @Transaction
    @Query("SELECT * FROM pokemon_data_table WHERE pokemonId = :pokemonId")
    fun getPokemonDataWithStats(pokemonId: String): PokemonDataWithStats

    @Transaction
    @Query("SELECT * FROM pokemon_data_table WHERE pokemonId = :pokemonId")
    fun getPokemonDataWithTypes(pokemonId: String): PokemonDataWithTypes

    @Transaction
    @Query("SELECT * FROM pokemon_data_table WHERE pokemonId = :pokemonId")
    fun getPokemonDataWithAbilities(pokemonId: String): PokemonDataWithAbilities
}
