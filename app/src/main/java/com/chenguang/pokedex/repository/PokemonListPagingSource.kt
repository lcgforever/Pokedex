package com.chenguang.pokedex.repository

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.network.model.toPokemonInfo
import timber.log.Timber

/**
 * Paging source to handle loading paged data for pokemon list
 */
class PokemonListPagingSource(
    private val pokemonNetworkClient: PokemonNetworkClient
) : PagingSource<Int, PokemonInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonInfo> {
        return try {
            val nextOffset = params.key ?: 0
            val networkResponse = pokemonNetworkClient.fetchPokemonList(nextOffset)
            if (networkResponse.isSuccessful) {
                val pokemonListResponse = networkResponse.body()!!
                LoadResult.Page(
                    data = pokemonListResponse.results.map { it.toPokemonInfo() },
                    prevKey = getOffset(pokemonListResponse.previous),
                    nextKey = getOffset(pokemonListResponse.next)
                )
            } else {
                LoadResult.Error(Throwable(networkResponse.message()))
            }
        } catch (e: Exception) {
            Timber.e("Failed to load new pokemon list data: $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            val pageSize = state.config.pageSize
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
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
