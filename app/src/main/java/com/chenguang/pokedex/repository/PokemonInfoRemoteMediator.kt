package com.chenguang.pokedex.repository

import android.net.Uri
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.chenguang.pokedex.db.AppDatabase
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.db.model.PokemonRemoteKey
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.toPokemonInfo
import javax.inject.Inject

private const val DEFAULT_QUERY = "pokemon"

@OptIn(ExperimentalPagingApi::class)
class PokemonInfoRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val pokemonNetworkClient: PokemonNetworkClient
) : RemoteMediator<Int, PokemonInfo>() {

    private val remoteKeyDao = database.pokemonRemoteKeyDao()
    private val pokemonInfoDao = database.pokemonInfoDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonInfo>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> {
                    // We don't prepend in our app since we always rely on REFRESH to load the first page
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKeyByQuery(DEFAULT_QUERY)
                    }
                    // If there is no offset, we have reached the end of pagination
                    if (remoteKey.offset == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.offset
                }
            }

            val networkResponse = pokemonNetworkClient.fetchPokemonList(loadKey)
            if (networkResponse.isSuccessful) {
                val pokemonListResponse = networkResponse.body()!!
                val nextOffset = getOffset(pokemonListResponse.next)
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        remoteKeyDao.deleteRemoteKeyByQuery(DEFAULT_QUERY)
                        pokemonInfoDao.deleteAll()
                    }
                    remoteKeyDao.insertOrReplaceRemoteKey(
                        PokemonRemoteKey(label = DEFAULT_QUERY, offset = nextOffset)
                    )
                    pokemonInfoDao.insertPokemonInfoList(pokemonListResponse.results.map { it.toPokemonInfo() })
                }
                MediatorResult.Success(endOfPaginationReached = nextOffset == null)
            } else {
                MediatorResult.Error(Throwable(networkResponse.message()))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getOffset(pageUrl: String?): Int? {
        return pageUrl?.let { url ->
            val uri = Uri.parse(url)
            val offsetQuery = uri.getQueryParameter("offset")
            return offsetQuery?.toInt()
        }
    }
}
