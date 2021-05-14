package com.chenguang.pokedex.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chenguang.pokedex.model.LoadDataUiState
import com.chenguang.pokedex.model.PokemonInfo
import com.chenguang.pokedex.network.PokemonNetworkClient
import com.chenguang.pokedex.repository.PokemonListPagingSource
import com.chenguang.pokedex.repository.PokemonRepository
import com.chenguang.pokedex.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonNetworkClient: PokemonNetworkClient,
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val mutableLoadDataStateFlow: MutableStateFlow<LoadDataUiState<List<PokemonInfo>>> =
        MutableStateFlow(LoadDataUiState.Success(emptyList()))
    private var currentPageNumber = 0

    val loadDataStateFlow: StateFlow<LoadDataUiState<List<PokemonInfo>>> = mutableLoadDataStateFlow

    val pokemonListDataFlow: Flow<PagingData<PokemonInfo>> = Pager(
        PagingConfig(pageSize = Constants.POKEMON_LIST_PAGE_SIZE)
    ) { PokemonListPagingSource(pokemonNetworkClient) }
        .flow
        .cachedIn(viewModelScope)

    fun fetchPokemonList() {
        viewModelScope.launch {
            pokemonRepository.fetchPokemonList(currentPageNumber)
                .distinctUntilChanged()
                .onStart {
                    mutableLoadDataStateFlow.value = LoadDataUiState.Loading()
                }
                .catch { error ->
                    mutableLoadDataStateFlow.value = LoadDataUiState.Error(error)
                }
                .collect { pokemonList ->
                    mutableLoadDataStateFlow.value = LoadDataUiState.Success(pokemonList)
                }
        }
    }
}
