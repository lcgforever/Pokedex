package com.chenguang.pokedex.model

/**
 * This represents different states for loading data UX
 */
sealed class LoadDataUiState<T> {

    class Loading<T> : LoadDataUiState<T>()

    data class Success<T>(val data: T) : LoadDataUiState<T>()

    data class Error<T>(val exception: Throwable) : LoadDataUiState<T>()
}
