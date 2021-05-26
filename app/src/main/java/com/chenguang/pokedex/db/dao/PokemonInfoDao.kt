package com.chenguang.pokedex.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chenguang.pokedex.db.model.PokemonInfo

@Dao
interface PokemonInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonInfoList(pokemonInfoList: List<PokemonInfo>)

    @Query("SELECT * FROM pokemon_info_table")
    fun pagingSource(): PagingSource<Int, PokemonInfo>

    @Query("DELETE FROM pokemon_info_table")
    fun deleteAll()
}
