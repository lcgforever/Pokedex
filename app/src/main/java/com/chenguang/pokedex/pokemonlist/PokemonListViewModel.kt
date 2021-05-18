package com.chenguang.pokedex.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chenguang.pokedex.model.PokemonInfo
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.repository.PokemonListPagingSource
import com.chenguang.pokedex.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonNetworkClient: PokemonNetworkClient
) : ViewModel() {

    val pokemonListDataFlow: Flow<PagingData<PokemonInfo>> = Pager(
        PagingConfig(pageSize = Constants.POKEMON_LIST_PAGE_SIZE)
    ) {
        PokemonListPagingSource(pokemonNetworkClient)
    }
        .flow
        .cachedIn(viewModelScope)
}
