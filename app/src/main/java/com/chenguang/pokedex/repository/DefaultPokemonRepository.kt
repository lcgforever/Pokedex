package com.chenguang.pokedex.repository

import androidx.annotation.VisibleForTesting
import androidx.room.withTransaction
import com.chenguang.pokedex.db.AppDatabase
import com.chenguang.pokedex.db.model.PokemonAbilityCrossRef
import com.chenguang.pokedex.db.model.PokemonTypeCrossRef
import com.chenguang.pokedex.model.PokemonDetails
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.toPokemonAbilityList
import com.chenguang.pokedex.network.model.toPokemonData
import com.chenguang.pokedex.network.model.toPokemonStatList
import com.chenguang.pokedex.network.model.toPokemonTypeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

/**
 * Default implementation for [PokemonRepository] to fetch pokemon data
 */
class DefaultPokemonRepository @Inject constructor(
    private val database: AppDatabase,
    private val pokemonNetworkClient: PokemonNetworkClient
) : PokemonRepository {

    private val pokemonDataDao = database.pokemonDataDao()

    override fun fetchPokemonData(pokemonId: String): Flow<PokemonDetails> {
        return flow {
            val pokemonDataFromDb = pokemonDataDao.getPokemonDataById(pokemonId)
            if (pokemonDataFromDb != null) {
                val pokemonDetails = queryPokemonFromDatabase(pokemonId)
                emit(pokemonDetails)
            } else {
                val networkResponse = pokemonNetworkClient.fetchPokemonDataById(pokemonId)
                if (networkResponse.isSuccessful) {
                    val pokemonResponse = networkResponse.body()!!

                    // Convert network response to database models
                    val pokemonData = pokemonResponse.toPokemonData()
                    val pokemonTypeList = pokemonResponse.toPokemonTypeList()
                    val pokemonTypeCrossRefList = pokemonTypeList.map {
                        PokemonTypeCrossRef(
                            pokemonId = pokemonId,
                            typeId = it.typeId
                        )
                    }
                    val pokemonStatList = pokemonResponse.toPokemonStatList()
                    val pokemonAbilityList = pokemonResponse.toPokemonAbilityList()
                    val pokemonAbilityCrossRefList = pokemonAbilityList.map {
                        PokemonAbilityCrossRef(
                            pokemonId = pokemonId,
                            abilityId = it.abilityId
                        )
                    }

                    database.withTransaction {
                        // Store all information in database
                        pokemonDataDao.insertPokemonData(pokemonData)
                        pokemonDataDao.insertPokemonStatList(pokemonStatList)
                        pokemonDataDao.insertPokemonTypeList(pokemonTypeList)
                        pokemonDataDao.insertPokemonTypeCrossRefList(pokemonTypeCrossRefList)
                        pokemonDataDao.insertPokemonAbilityList(pokemonAbilityList)
                        pokemonDataDao.insertPokemonAbilityCrossRefList(pokemonAbilityCrossRefList)
                    }

                    val pokemonDetails = queryPokemonFromDatabase(pokemonId)
                    emit(pokemonDetails)
                } else {
                    Timber.e("Fetch pokemon data failed: ${networkResponse.message()}")
                    error("Failed to fetch pokemon data by id")
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    @VisibleForTesting
    fun queryPokemonFromDatabase(pokemonId: String): PokemonDetails {
        val pokemonDataWithStats = pokemonDataDao.getPokemonDataWithStats(pokemonId)
        val pokemonDataWithTypes = pokemonDataDao.getPokemonDataWithTypes(pokemonId)
        val pokemonDataWithAbilities = pokemonDataDao.getPokemonDataWithAbilities(pokemonId)
        return PokemonDetails(
            id = pokemonId,
            name = pokemonDataWithStats.pokemonData.name,
            baseExperience = pokemonDataWithStats.pokemonData.baseExperience,
            height = pokemonDataWithStats.pokemonData.height,
            weight = pokemonDataWithStats.pokemonData.weight,
            stats = pokemonDataWithStats.stats,
            types = pokemonDataWithTypes.types,
            abilities = pokemonDataWithAbilities.abilities
        )
    }
}
