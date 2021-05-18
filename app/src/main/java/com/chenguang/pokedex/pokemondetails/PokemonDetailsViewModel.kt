package com.chenguang.pokedex.pokemondetails

import androidx.lifecycle.ViewModel
import com.chenguang.pokedex.model.PokemonDetails
import com.chenguang.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    fun fetchPokemonDetails(
        pokemonId: String,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (error: Throwable) -> Unit
    ): Flow<PokemonDetails> {
        return pokemonRepository.fetchPokemonData(pokemonId)
            .onStart { onStart() }
            .onCompletion { onCompletion() }
            .catch { onError(it) }
    }
}
