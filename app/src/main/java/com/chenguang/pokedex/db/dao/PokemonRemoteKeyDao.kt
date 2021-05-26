package com.chenguang.pokedex.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chenguang.pokedex.db.model.PokemonRemoteKey

/**
 * [Dao] interfaces to store/access Pokemon paging related remote key object
 */
@Dao
interface PokemonRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceRemoteKey(remoteKey: PokemonRemoteKey)

    @Query("SELECT * FROM pokemon_remote_key_table WHERE label = :query")
    suspend fun getRemoteKeyByQuery(query: String): PokemonRemoteKey

    @Query("DELETE FROM pokemon_remote_key_table WHERE label = :query")
    suspend fun deleteRemoteKeyByQuery(query: String)
}
