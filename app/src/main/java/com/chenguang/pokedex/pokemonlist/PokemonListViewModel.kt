package com.chenguang.pokedex.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chenguang.pokedex.db.AppDatabase
import com.chenguang.pokedex.db.model.PokemonInfo
import com.chenguang.pokedex.repository.PokemonInfoRemoteMediator
import com.chenguang.pokedex.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    database: AppDatabase,
    pokemonInfoRemoteMediator: PokemonInfoRemoteMediator
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pokemonListDataFlow: Flow<PagingData<PokemonInfo>> = Pager(
        config = PagingConfig(pageSize = Constants.POKEMON_LIST_PAGE_SIZE),
        remoteMediator = pokemonInfoRemoteMediator
    ) {
        database.pokemonInfoDao().pagingSource()
    }
        .flow
        .cachedIn(viewModelScope)
}
